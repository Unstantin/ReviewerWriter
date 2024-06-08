package com.example.reviewerwriter.domain.entites

data class ReviewCardEntity(
    val id : String,
    val title: String?,
    val shortText: String?,
    val authorNickname: String?,
    val photos: List<String>?
)
