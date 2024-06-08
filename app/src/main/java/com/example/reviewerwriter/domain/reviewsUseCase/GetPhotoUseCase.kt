package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.ByteArrayEntity
import com.example.reviewerwriter.domain.entites.Status

class GetPhotoUseCase (private val repo: ReviewsRepository) {
    fun execute(photo: String, callback: (Status<ByteArrayEntity>) -> Unit){
        repo.getPhoto(photo,callback)
    }
}