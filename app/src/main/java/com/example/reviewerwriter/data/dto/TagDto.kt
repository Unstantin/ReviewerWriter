package com.example.reviewerwriter.data.dto

import com.example.reviewerwriter.domain.etites.Tag

data class TagDto(
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