package com.example.reviewerwriter.domain.etites

data class SaveTagsEntity(
    val tags:  List<Tag>
)
data class Criteria(
    val name: String,
    val value: Int?
)

data class Tag(
    val name: String,
    val criteria: List<Criteria>
)