package com.example.reviewerwriter.data.dto

data class GetTagDto(
    val nickname: String?,
    val tags:  List<Tag>?
)
data class Criteria(
    val name: String?,
    val value: Int?
)

data class Tag(
    val name: String?,
    val criteria: List<Criteria>?
)