package com.example.reviewerwriter.ui.serviceScreen.criteria

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewerwriter.ui.serviceScreen.TagsCriteriaViewModel
import com.example.reviewerwriter.ui.utils.CriteriaData
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.launch

class CriteriaViewModel(
    private val criteriaData: CriteriaData,
    private val tagsCriteriaViewModel: TagsCriteriaViewModel
) : ViewModel(), showToastMessage {

    override val _showToastMessage = MutableLiveData<String>()
    val addCriteriaTextField =  mutableStateOf("")
    val criteriaList = mutableStateOf(listOf<String>())

    init {
        // Загрузка критериев из CriteriaData при инициализации ViewModel
        viewModelScope.launch {
            criteriaData.criteriaDto.collect { criteria ->
                criteriaList.value = criteria
            }
        }
    }
    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }
    fun addCriteria(criteria: String) {
        if (criteria !in criteriaList.value) {
            criteriaData.addCriteria(criteria)
            // Обновление списка критериев в CriteriaViewModel
            viewModelScope.launch {
                criteriaData.criteriaDto.collect { updatedCriteriaList ->
                    criteriaList.value = updatedCriteriaList
                }
            }
            tagsCriteriaViewModel.addNewCriteriaToData(criteriaList.value)
        } else {
            _showToastMessage.value = "Такой критерий уже есть"
        }
    }

    fun removeCriteria(criteria: String) {
        // Удаление критерия из CriteriaData
        criteriaData.removeCriteria(criteria)
        // Обновление списка критериев
        criteriaList.value = criteriaList.value.filter { it != criteria }
    }
}
