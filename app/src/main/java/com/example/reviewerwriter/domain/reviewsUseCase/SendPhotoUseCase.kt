package com.example.reviewerwriter.domain.reviewsUseCase

import com.example.reviewerwriter.domain.entites.PhotoFileNameEntity
import com.example.reviewerwriter.domain.entites.Status
import okhttp3.MultipartBody

class SendPhotoUseCase (private val repo: ReviewsRepository){
    fun execute(photo: MultipartBody, callback: (Status<PhotoFileNameEntity>) -> Unit){
        repo.sendPhoto(photo,callback)
    }
}