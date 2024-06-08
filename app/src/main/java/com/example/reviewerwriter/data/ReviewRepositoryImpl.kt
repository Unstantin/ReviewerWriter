package com.example.reviewerwriter.data

import android.util.Log
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.entites.ByteArrayEntity
import com.example.reviewerwriter.domain.entites.PhotoFileNameEntity
import com.example.reviewerwriter.domain.entites.ReviewCardEntity
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.Status
import com.example.reviewerwriter.domain.reviewsUseCase.ReviewsRepository
import com.example.reviewerwriter.ui.utils.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ReviewRepositoryImpl(private val tokenStorage: TokenStorage): ReviewsRepository {
    private val mainApi = RetrofitFactory.getMainApi()
    override fun getAllReviews(callback: (Status<List<ReviewCardEntity>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.getAllReviews(
                        "Bearer ${tokenStorage.getToken()}")
                    if (response.isSuccessful) {
                        callback(Status(response.code(), response.body() , null))
                        Log.w("response.body()", response.body().toString())
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

    override fun sendPhoto(photo: MultipartBody, callback: (Status<PhotoFileNameEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.sendPhoto(
                        "Bearer ${tokenStorage.getToken()}",
                        photo
                    )
                    if (response.isSuccessful) {
                        callback(Status(response.code(), PhotoFileNameEntity(filename = response.body()?.filename.toString()), null))
                    } else {
                        callback(
                            Status(
                                response.code(),
                                null,
                                Exception(response.errorBody()?.string())
                            )
                        )
                    }
                } else {
                    callback(Status(-1, null, Exception("Токен отсутствует")))
                }
            } catch (e: Exception) {
                Log.w("ошибка", e.toString())
                callback(Status(-1, null, e))
            }
        }
    }

    override fun getPhoto(photo: String, callback: (Status<ByteArrayEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.getPhoto(
                        "Bearer ${tokenStorage.getToken()}",
                        photo
                    )
                    Log.w("бади", response.body().toString())
                    if (response.isSuccessful) {
                        callback(Status(response.code(), ByteArrayEntity(response.body()!!), null))
                    } else {
                        callback(
                            Status(
                                response.code(),
                                null,
                                Exception(response.errorBody()?.string())
                            )
                        )
                    }
                } else {
                    callback(Status(-1, null, Exception("Токен отсутствует")))
                }
            } catch (e: Exception) {
                callback(Status(-1, null, e))
            }
        }
    }

}