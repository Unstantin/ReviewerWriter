package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status

class AddReviewUseCase(private val repo: ReviewsRepository) {
    fun execute(reviewDto: ReviewDtoEntity, callback: (Status<Unit>) -> Unit){
        repo.addReview(reviewDto,callback)
    }
}