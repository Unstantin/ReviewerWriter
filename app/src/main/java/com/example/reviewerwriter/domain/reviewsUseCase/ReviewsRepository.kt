package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.ByteArrayEntity
import com.example.reviewerwriter.domain.entites.PhotoFileNameEntity
import com.example.reviewerwriter.domain.entites.ReviewCardEntity
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status
import okhttp3.MultipartBody

interface ReviewsRepository {
    fun getAllReviews(callback: (Status<List<ReviewCardEntity>>) -> Unit)
    fun getReviewById()
    fun addReview(reviewDto: ReviewDtoEntity, callback: (Status<Unit>) -> Unit)
    fun sendPhoto(photo: MultipartBody, callback: (Status<PhotoFileNameEntity>) -> Unit)
    fun getPhoto(photo: String, callback: (Status<ByteArrayEntity>) -> Unit)
}