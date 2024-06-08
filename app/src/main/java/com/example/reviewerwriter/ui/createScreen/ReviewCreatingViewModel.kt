package com.example.reviewerwriter.ui.createScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewerwriter.domain.entites.CriteriaEntity
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.TagEntity
import com.example.reviewerwriter.domain.reviewsUseCase.AddReviewUseCase
import com.example.reviewerwriter.domain.reviewsUseCase.SendPhotoUseCase
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ReviewCreatingViewModel(
    private val addReviewUseCase: AddReviewUseCase,
    private val sendPhotoUseCase: SendPhotoUseCase,
) : ViewModel(), showToastMessage {
    override val _showToastMessage = MutableLiveData<String>()
    var expandedTag = mutableStateOf(false)
    val iconList = listOf(
        "-",
        "1",
        "2",
        "3",
        "4",
        "5"
    )
    var onSelectedCriteria = mutableStateOf("")
    var onSelectedTag = mutableStateOf("")
    var expandedSelectStar = mutableStateOf(false)
    var reviewTitle = mutableStateOf("")
    var reviewDescriptionField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var selectedText = mutableStateOf(iconList[0])
    var selectedImage = mutableStateOf<List<Bitmap>>(emptyList())
    val tags = mutableStateOf(emptyList<TagEntity>())
    var showCameraPreview = mutableStateOf(false)
    var listPhotosNames = mutableStateOf(emptyList<String>())
    var mainRate = mutableStateOf("0.0")

    fun takePhoto(
        controller: LifecycleCameraController,
        context: Context
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    selectedImage.value = selectedImage.value + rotatedBitmap
                    showCameraPreview.value = false
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ", exception)
                    showCameraPreview.value = false
                }
            }
        )
    }
    fun calculateAverageRating() {
        val totalSum = tags.value.flatMap { tag ->
            tag.criteria?.mapNotNull { it.value } ?: emptyList()
        }.sum()

        val totalCount = tags.value.flatMap { tag ->
            tag.criteria?.mapNotNull { it.value } ?: emptyList()
        }.count()

        if (totalCount > 0) {
            val average = totalSum.toDouble() / totalCount
            mainRate.value = String.format("%.1f", average)
        } else {
            mainRate.value = "0.0"
        }
    }
    fun addImageUris(uris: List<Uri>, context: Context) {
        // Преобразование URI в Bitmap и добавление в selectedImageUris
        val bitmaps = uris.mapNotNull { uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
        selectedImage.value = selectedImage.value + bitmaps
    }

    fun addTag(tag: String, mapTagsCriteria: Map<String, List<String>>) {
        // Извлекаем критерии из mapTagsCriteria, которые соответствуют имени тега
        val newCriteria = mapTagsCriteria[tag]?.map { name ->
            CriteriaEntity(name = name, value = 5)
        }

        val newTag = TagEntity(
            name = tag,
            criteria = newCriteria ?: emptyList()
        )

        // Добавляем только новый тег, без дублирования критериев
        tags.value = tags.value + newTag
        calculateAverageRating()
    }
    fun removeTag(tag: String){
        tags.value = tags.value.filter { it.name != tag }
        calculateAverageRating()
    }

    fun updateCriteriaRating(tagName: String, criteriaName: String, newRating: Int) {
        // Обновляем рейтинг критерия внутри тега
        tags.value = tags.value.map { tag ->
            if (tag.name == tagName) {
                tag.copy(criteria = tag.criteria?.map { crit ->
                    if (crit.name == criteriaName) {
                        crit.copy(value = newRating)
                    } else crit
                })
            } else tag
        }
        calculateAverageRating()
    }

    fun removeCriteriaFromTag(tagName: String, criteriaName: String) {
        // Удаляем критерий из тега
        tags.value = tags.value.map { tag ->
            if (tag.name == tagName) {
                tag.copy(criteria = tag.criteria?.filter { crit ->
                    crit.name != criteriaName
                })
            } else tag
        }
        calculateAverageRating()
    }
    fun onButtonSaveClick(){
        sendPhotosOnServer()
        val reviewDto = ReviewDtoEntity(
            title = reviewTitle.value,
            mainText = reviewDescriptionField.value,
            shortText = "", // Если у вас есть короткий текст, вы должны его преобразовать и добавить здесь
            tags = tags.value,
            photos = listPhotosNames.value
        )
        CoroutineScope(Dispatchers.IO).launch {
            addReviewUseCase.execute(reviewDto){status ->
                when(status.statusCode){
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                _showToastMessage.value =
                                    "Рецензия сохранена"
                            }
                        }
                    }
                    else -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                // Обработка ошибки
                                _showToastMessage.value =
                                    "Ошибка: ${status.errors?.message}"
                            }
                        }
                    }
                }
            }
        }
    }
    fun sendPhotosOnServer() {
        selectedImage.value.forEach { bitmap ->
            viewModelScope.launch {
                try {
                    // Создаем MultipartBody с одной частью изображения
                    val multipartBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", "image.jpg", bitmap.toRequestBody())
                        .build()

                    // Отправляем изображение на сервер
                    sendPhotoUseCase.execute(multipartBody) { status ->
                        when (status.statusCode) {
                            200 -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    withContext(Dispatchers.Main) {
                                        listPhotosNames.value =
                                            listPhotosNames.value + status.value!!
                                        Log.w("Строка имя фотки",status.value)
                                    }
                                }
                            }
                            else -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    withContext(Dispatchers.Main) {
                                        _showToastMessage.value =
                                            "Ошибка: ${status.errors?.message}"
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    // Вызов тоста в основном потоке
                    withContext(Dispatchers.Main) {
                        _showToastMessage.value = "Ошибка: ${e.message}"
                        e.message?.let { Log.w("ошибка catch", it) }
                    }
                }
            }
        }
    }
    fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    fun Bitmap.toRequestBody(): RequestBody {
        val byteArray = this.toByteArray()
        return byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
    }

    fun Bitmap.toMultipartBody(partName: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(partName, "image.jpg", this.toRequestBody())
    }

    fun List<Bitmap>.toMultipartBodyList(partName: String): List<MultipartBody.Part> {
        return this.mapIndexed { index, bitmap ->
            bitmap.toMultipartBody("$partName[$index]")
        }
    }

    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
}