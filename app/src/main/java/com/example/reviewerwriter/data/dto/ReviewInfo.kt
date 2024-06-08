package com.example.reviewerwriter.data.dto

data class ReviewInfo(
    val id: Int,
    val title: String,
    val mainText: String,
    val shortText: String,
    val authorNickname: String,
    val date: String,
    val tags: ArrayList<TagEntity>,
    val likesN: Int,
    val photos: ArrayList<String>
)

data class CriteriaEntity(
    val name: String?,
    val value: Int?
)
data class TagEntity(
    val name: String,
    val criteria: List<CriteriaEntity>?
)