package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.domain.entites.SaveTagsEntity
import com.example.reviewerwriter.domain.entites.Status

interface TagsRepository {
    fun getTags(callbaсk: (Status<SaveTagsEntity>) -> Unit)
    fun setTags(tagDto: SaveTagsEntity, callbaсk: (Status<Unit>) -> Unit)
}