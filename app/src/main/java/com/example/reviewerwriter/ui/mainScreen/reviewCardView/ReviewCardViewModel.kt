package com.example.reviewerwriter.ui.mainScreen.reviewCardView

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.domain.reviewsUseCase.GetPhotoUseCase
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewCardViewModel(
    private val getPhotoUseCase: GetPhotoUseCase
): ViewModel(), showToastMessage {
    override val _showToastMessage = MutableLiveData<String>()
    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
    private val _bitmapList = MutableStateFlow<List<ByteArray>>(emptyList())
    val bitmapList: StateFlow<List<ByteArray>> = _bitmapList

    fun getBitmapList(stringList: List<String>) {
        val imageBitmaps = mutableListOf<ByteArray>()
        stringList.forEach { imageName ->
            getPhotoUseCase.execute(imageName) { status ->
                when (status.statusCode) {
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                status.value?.let { bitmap ->
                                    imageBitmaps.add(bitmap.byteArray)
                                    Log.w("бинарники",imageBitmaps.toString() )
                                }
                            }
                        }
                    }

                    else -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                _showToastMessage.value =
                                    "Ошибка: ${status.errors?.message}"
                                status.errors?.message?.let { Log.w("ошибка бинариники", it) }
                            }
                        }
                    }
                }
            }
        }
    }
}