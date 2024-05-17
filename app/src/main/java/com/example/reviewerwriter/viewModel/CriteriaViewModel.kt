package com.example.reviewerwriter.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.utils.showToastMessage

class CriteriaViewModel() : ViewModel(), showToastMessage {

    override val _showToastMessage = MutableLiveData<String>()
    val addCriteriaTextField =  mutableStateOf("")
    var сriteriaList = mutableStateOf(listOf<String>(

    ))
    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
    fun addCritaria(tag: String){
        if (tag !in сriteriaList.value) {
            сriteriaList.value = сriteriaList.value + tag
        }else{
            _showToastMessage.value = "Такой критерий уже есть"
        }
    }
    fun removeCritaria(critaria: String){
        сriteriaList.value = сriteriaList.value.filter { it != critaria }
    }
}
