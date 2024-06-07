package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status

interface ReviewsRepository {
    fun getAllReviews()
    fun getReviewById()
    fun addReview(reviewDto: ReviewDtoEntity, callback: (Status<Unit>) -> Unit)
}