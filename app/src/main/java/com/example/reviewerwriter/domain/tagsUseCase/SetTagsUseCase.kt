package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status

class SetTagsUseCase (private val repo : TagsRepository) {

    fun execute(tagDto: SaveTagsEntity, callbak: (Status<Unit>) -> Unit){
        repo.setTags(tagDto, callbak)
    }
}