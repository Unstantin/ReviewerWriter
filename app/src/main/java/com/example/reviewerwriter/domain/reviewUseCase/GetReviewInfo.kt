package com.example.reviewerwriter.domain.reviewUseCase

import com.example.reviewerwriter.data.dto.ReviewInfo
import com.example.reviewerwriter.domain.etites.Status

class GetReviewInfo(private val repo: ReviewRepository) {
    fun execute(id: Int, callback: (Status<ReviewInfo>) -> Unit) {

    }
}