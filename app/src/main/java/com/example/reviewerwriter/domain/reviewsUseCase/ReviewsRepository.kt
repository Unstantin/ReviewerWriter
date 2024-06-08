package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.data.dto.ReviewInfo
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status

interface ReviewsRepository {
    fun getAllReviews()
    fun addReview(reviewDto: ReviewDtoEntity, callback: (Status<Unit>) -> Unit)
    fun getReviewById(id: Int, callback: (Status<ReviewInfo>) -> Unit)
}