package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.ReviewCardEntity
import com.example.reviewerwriter.domain.entites.Status

class GetAllReviews (private val repo: ReviewsRepository) {
    fun execute(callback: (Status<List<ReviewCardEntity>>) -> Unit){
        repo.getAllReviews(callback)
    }
}