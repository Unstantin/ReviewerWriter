package com.example.reviewerwriter.domain.tagsUseCase

import com.example.reviewerwriter.data.dto.TagDto
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status

interface TagsRepository {
//    fun getTags(callbaсk: (Status<???>) -> Unit)
    fun setTags(tagDto: TagDto, callbaсk: (Status<SaveTagsEntity>) -> Unit)
}