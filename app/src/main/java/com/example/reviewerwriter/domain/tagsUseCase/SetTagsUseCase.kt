package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.data.dto.TagDto
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status

class SetTagsUseCase (private val repo : TagsRepository) {

    fun execute(tagDto: TagDto, callbak: (Status<SaveTagsEntity>) -> Unit){
        repo.setTags(tagDto, callbak)
    }
}