package com.example.reviewerwriter.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.utils.showToastMessage

class TagsViewModel : ViewModel(), showToastMessage{

    override val _showToastMessage = MutableLiveData<String>()
    val addTagTextField =  mutableStateOf("")
    var expanded =  mutableStateOf(false)
    var selectedCriteria =  mutableStateOf(listOf<String>())
    var criteriaList = mutableStateOf(listOf<String>(
        "критерий 1",
        "критерий 2 два",
        "криетрий 3",
        "новый критерий"
    ))
    var mapTagsCriteria = mutableStateOf<Map<String,List<String>>>(emptyMap())
    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
    fun addTagCriteria(tag: String, criteria: List<String>) {
        val currentMap = mapTagsCriteria.value.toMutableMap()
        if (!currentMap.containsKey(tag)) {
            currentMap[tag] = criteria
        }
        mapTagsCriteria.value = currentMap
    }

    fun removeTagCriteria(tag: String) {
        val currentMap = mapTagsCriteria.value.toMutableMap()
        if (currentMap.containsKey(tag)) {
            currentMap.remove(tag)
        }
        mapTagsCriteria.value = currentMap
    }
    fun removeValueFromTagCriteria(tag: String, criteria: String) {
        val currentMap = mapTagsCriteria.value.toMutableMap()
        if (currentMap.containsKey(tag)) {
            val updatedCriteria = currentMap[tag]?.filter { it != criteria }
            if (updatedCriteria != null) {
                currentMap[tag] = updatedCriteria
            }
        }
        mapTagsCriteria.value = currentMap
    }
    fun addC(c: String){
        selectedCriteria.value = selectedCriteria.value + c
    }
    fun removeC(c: String){
        selectedCriteria.value = selectedCriteria.value.filter { it != c }
    }
}