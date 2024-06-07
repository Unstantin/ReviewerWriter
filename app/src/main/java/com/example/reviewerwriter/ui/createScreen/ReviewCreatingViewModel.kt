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
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.domain.entites.CriteriaEntity
import com.example.reviewerwriter.domain.entites.TagEntity
import com.example.reviewerwriter.domain.reviewsUseCase.AddReviewUseCase

class ReviewCreatingViewModel(
    private val addReviewUseCase: AddReviewUseCase
) : ViewModel() {
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
    }
    fun removeTag(tag: String){
        tags.value = tags.value.filter { it.name != tag }
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
    }

}