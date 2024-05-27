package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status

class GetTagsUseCase (private val repo : TagsRepository){
    fun execute(callbak: (Status<SaveTagsEntity>) -> Unit){
        repo.getTags(callbak)
    }
}