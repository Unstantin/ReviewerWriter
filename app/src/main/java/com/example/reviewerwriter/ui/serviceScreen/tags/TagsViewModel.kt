package com.example.reviewerwriter.ui.serviceScreen.tags

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.domain.etites.CriteriaEntity
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.TagEntity
import com.example.reviewerwriter.domain.tagsUseCase.SetTagsUseCase
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagsViewModel(
    private val setTagsUseCase: SetTagsUseCase
) : ViewModel(), showToastMessage {
    override val _showToastMessage = MutableLiveData<String>()
    val addTagTextField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var selectedCriteria = mutableStateOf(listOf<String>())
    var mapTagsCriteria = mutableStateOf<Map<String, List<String>>>(emptyMap())

    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }

    fun addTagCriteria(tag: String, criteria: List<String>) {
        if (addTagTextField.value in mapTagsCriteria.value.keys) {
            _showToastMessage.value = "Такой тег уже есть"
        } else {
            val currentMap = mapTagsCriteria.value.toMutableMap()
            if (!currentMap.containsKey(tag)) {
                currentMap[tag] = criteria
            }
            mapTagsCriteria.value = currentMap
            sendTagsWithCriteria()
        }
    }

    fun removeTagCriteria(tag: String) {
        val currentMap = mapTagsCriteria.value.toMutableMap()
        if (currentMap.containsKey(tag)) {
            currentMap.remove(tag)
        }
        mapTagsCriteria.value = currentMap
        sendTagsWithCriteria()
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
        sendTagsWithCriteria()

    }

    fun addC(c: String) {
        selectedCriteria.value = selectedCriteria.value + c
    }

    fun removeC(c: String) {
        selectedCriteria.value = selectedCriteria.value.filter { it != c }
    }

    fun sendTagsWithCriteria() {
        // Преобразуем mapTagsCriteria в TagDto
        val tagDto = SaveTagsEntity(mapTagsCriteria.value.map { (name, criteria) ->
            TagEntity(name, criteria.map { CriteriaEntity(it, null) })
        }.toList())

        CoroutineScope(Dispatchers.IO).launch {
            setTagsUseCase.execute(tagDto){status ->
                when(status.statusCode){
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                _showToastMessage.value =
                                    "Теги обновлены"
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