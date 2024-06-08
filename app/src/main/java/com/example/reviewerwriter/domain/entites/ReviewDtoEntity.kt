package com.example.reviewerwriter.domain.entites

data class ReviewDtoEntity(
    val title: String,
    val mainText: String,
    val shortText: String,
    val tags: List<TagEntity>,
    val photos: List<String>
)
