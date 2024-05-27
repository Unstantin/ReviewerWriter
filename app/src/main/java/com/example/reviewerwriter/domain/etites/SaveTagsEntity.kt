package com.example.reviewerwriter.domain.etites

data class SaveTagsEntity(
    val tags:  List<TagEntity>
)
data class CriteriaEntity(
    val name: String?,
    val value: Int?
)
data class TagEntity(
    val name: String,
    val criteria: List<CriteriaEntity>?
)