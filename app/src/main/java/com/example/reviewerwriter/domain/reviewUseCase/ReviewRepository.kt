package com.example.reviewerwriter.domain.reviewUseCase

import com.example.reviewerwriter.data.dto.ReviewInfo
import com.example.reviewerwriter.domain.etites.Status

interface ReviewRepository {
    suspend fun getReviewById(id: Int): Status<ReviewInfo>
}