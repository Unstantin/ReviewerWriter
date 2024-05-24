package com.example.reviewerwriter.ui.serviceScreen.tags

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.data.TagsRepositoryImpl
import com.example.reviewerwriter.data.dto.TagDto
import com.example.reviewerwriter.domain.etites.Criteria
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status
import com.example.reviewerwriter.domain.etites.Tag
import com.example.reviewerwriter.ui.utils.showToastMessage

class TagsViewModel() : ViewModel(), showToastMessage {
    override val _showToastMessage = MutableLiveData<String>()
    val addTagTextField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var selectedCriteria = mutableStateOf(listOf<String>())
    var mapTagsCriteria = mutableStateOf<Map<String, List<String>>>(emptyMap())
    private val tagsRepository = TagsRepositoryImpl()

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
            sendTagsWithCriteria { _showToastMessage.value = it.statusCode.toString() }
        }
    }

    fun removeTagCriteria(tag: String) {
        val currentMap = mapTagsCriteria.value.toMutableMap()
        if (currentMap.containsKey(tag)) {
            currentMap.remove(tag)
        }
        mapTagsCriteria.value = currentMap
        sendTagsWithCriteria { _showToastMessage.value = it.statusCode.toString() }
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
        sendTagsWithCriteria { _showToastMessage.value = it.statusCode.toString() }

    }

    fun addC(c: String) {
        selectedCriteria.value = selectedCriteria.value + c
    }

    fun removeC(c: String) {
        selectedCriteria.value = selectedCriteria.value.filter { it != c }
    }

    fun sendTagsWithCriteria(callback: (Status<SaveTagsEntity>) -> Unit) {
        // Преобразуем mapTagsCriteria в TagDto
        val tagDto = TagDto(mapTagsCriteria.value.map { (name, criteria) ->
            Tag(name, criteria.map { Criteria(it, null) })
        }.toList())

        // Используем TagsRepositoryImpl для отправки тегов на сервер
        tagsRepository.setTags(tagDto) { status ->
            // Вызываем callback с результатом ответа сервера
            callback(status)
        }
    }
}