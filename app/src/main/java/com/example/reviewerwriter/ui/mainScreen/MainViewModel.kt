package com.example.reviewerwriter.ui.mainScreen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.domain.entites.ReviewCardEntity
import com.example.reviewerwriter.domain.reviewsUseCase.GetAllReviews
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (
    private val getAllReviews: GetAllReviews,
) : ViewModel(), showToastMessage {

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)
    override val _showToastMessage = MutableLiveData<String>()
    var reviewCards = mutableStateOf(listOf<ReviewCardEntity>())
    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
    init {
        CoroutineScope(Dispatchers.IO).launch{
            getAllReviews.execute {status ->
                when(status.statusCode){
                    200->{
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                reviewCards.value = status.value!!
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
}

