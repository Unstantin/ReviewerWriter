package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status
import com.example.reviewerwriter.domain.reviewsUseCase.ReviewsRepository
import com.example.reviewerwriter.ui.utils.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewRepositoryImpl(private val tokenStorage: TokenStorage): ReviewsRepository {
    private val mainApi = RetrofitFactory.getMainApi()
    override fun getAllReviews() {
        TODO("Not yet implemented")
    }

    override fun getReviewById() {
        TODO("Not yet implemented")
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
            }catch (e: Exception){
                callback(Status(-1, null, e))
            }
        }
    }

}