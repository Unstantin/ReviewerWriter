package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.dto.TagDto
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status
import com.example.reviewerwriter.domain.tagsUseCase.TagsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagsRepositoryImpl : TagsRepository {

    private val mainApi = RetrofitFactory.getMainApi()

    override fun setTags(tagDto: TagDto, callback: (Status<SaveTagsEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mainApi.setTags(tagDto)
                if (response.isSuccessful) {
                    callback(Status(response.code(), SaveTagsEntity(tagDto.tags ?: emptyList()), null))
                } else {
                    callback(Status(response.code(), null, Exception(response.errorBody().toString())))
                }
            } catch (e: Exception) {
                callback(Status(-1, null, e))
            }
        }
    }
}