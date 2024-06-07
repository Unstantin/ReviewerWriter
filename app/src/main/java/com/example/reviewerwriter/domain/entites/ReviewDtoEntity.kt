package com.example.reviewerwriter.domain.entites

data class ReviewDtoEntity(
    val title: String,
    val mainText: String,
    val shortText: String,
    val tags: SaveTagsEntity,
    val photos: List<String>
)
