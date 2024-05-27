package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status

interface TagsRepository {
    fun getTags(callbaсk: (Status<SaveTagsEntity>) -> Unit)
    fun setTags(tagDto: SaveTagsEntity, callbaсk: (Status<Unit>) -> Unit)
}