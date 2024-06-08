package com.example.reviewerwriter.ui.serviceScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewerwriter.domain.entites.SaveTagsEntity
import com.example.reviewerwriter.domain.entites.TagEntity
import com.example.reviewerwriter.domain.tagsUseCase.GetTagsUseCase
import com.example.reviewerwriter.ui.utils.CriteriaData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagsCriteriaViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val criteriaData: CriteriaData
): ViewModel() {
    var mapTagsCriteria = mutableStateOf<Map<String, List<String>>>(emptyMap())
    var criteriaList = mutableStateOf(listOf<String>())

    fun getTagsCriteriaFromServer(){
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                getTagsUseCase.execute { status ->
                    when(status.statusCode){
                        200->{
                            status.value?.tags
                            val saveTagsEntity = status.value as SaveTagsEntity
                            processTagsCriteria(saveTagsEntity.tags)
                            extractCriteria(saveTagsEntity.tags)
                        }
                        else ->{
                            Log.w("Теги с сервера", "ошибка ${status.errors}")
                        }
                    }
                }
            }
        }
    }
    private fun processTagsCriteria(tags: List<TagEntity>) {
        val map = mutableMapOf<String, List<String>>()
        tags.forEach { tag ->
            val criteriaNames = tag.criteria?.map { it.name ?: "" } ?: emptyList()
            map[tag.name] = criteriaNames
        }
        mapTagsCriteria.value = map
    }

    private fun extractCriteria(tags: List<TagEntity>) {
        val allCriteria = mutableListOf<String>()
        tags.forEach { tag ->
            tag.criteria?.forEach { criteria ->
                allCriteria.add(criteria.name ?: "")
            }
        }
        criteriaList.value = allCriteria

        //addNewCriteriaToData()
    }
    fun addNewCriteriaToData(newCriteriaList: List<String>) {
        criteriaList.value = newCriteriaList
        /* для обновления списка критериев при постоянных запросах, а не единоразовом
        viewModelScope.launch(Dispatchers.IO) {
            val newCriteria = сriteriaList.value.filter { criteria ->
                !criteriaData.criteriaDto.value.contains(criteria)
            }
            if (newCriteria.isNotEmpty()) {
                // Обновление списка критериев в criteriaData
                newCriteria.forEach {
                    criteriaData.addCriteria(it)
                }
            }
        }*/
    }
}