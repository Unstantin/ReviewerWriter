package com.example.reviewerwriter.data

import android.util.Log
import com.example.reviewerwriter.data.dto.ReviewInfo
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status
import com.example.reviewerwriter.domain.reviewsUseCase.ReviewsRepository
import com.example.reviewerwriter.ui.utils.TokenStorage
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Multipart

class ReviewRepositoryImpl(private val tokenStorage: TokenStorage): ReviewsRepository {
    private val mainApi = RetrofitFactory.getMainApi()
    override fun getAllReviews() {
        TODO("Not yet implemented")
    }

    override fun getReviewById(
        id: Int,
        callback: (Status<ReviewInfo>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mainApi.getReview("Bearer ${tokenStorage.getToken()}", id)
                if(response.isSuccessful) {
                    callback(Status(response.code(), response.body()?.let {
                        ReviewInfo(
                            id = it.id,
                            title = it.title,
                            mainText =it.mainText,
                            tags = it.tags,
                            likesN = it.likesN,
                            authorNickname = it.authorNickname,
                            date = it.date,
                            photos = it.photos,
                            shortText = it.shortText
                        )
                    }, null))
                } else {
                    callback(
                        Status(
                            response.code(),
                            null,
                            Exception(response.errorBody()?.string())
                        )
                    )
                }
            } catch (e: Exception){
                callback(Status(-1, null, e))
            }
        }
    }

    override fun addReview(reviewDto: ReviewDtoEntity, callback: (Status<Unit>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.addReview(
                        "Bearer ${tokenStorage.getToken()}",
                        reviewDto)
                    if (response.isSuccessful) {
                        callback(Status(response.code(), null, null))
                    }
                    else {
                        callback(
                            Status(
                                response.code(),
                                null,
                                Exception(response.errorBody()?.string())
                            )
                        )
                    }
                }
                else{
                    callback(Status(-1, null,Exception("Токен отсутствует")))
                }
            } catch (e: Exception){
                callback(Status(-1, null, e))
            }
        }
    }
}