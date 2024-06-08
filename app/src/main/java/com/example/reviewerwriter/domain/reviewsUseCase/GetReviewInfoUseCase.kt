package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.data.dto.ReviewInfo
import com.example.reviewerwriter.domain.entites.Status

class GetReviewInfoUseCase(private val repo : ReviewsRepository) {
    fun execute(id: Int, callback: (Status<ReviewInfo>) -> Unit) {
        repo.getReviewById(id, callback)
    }
}