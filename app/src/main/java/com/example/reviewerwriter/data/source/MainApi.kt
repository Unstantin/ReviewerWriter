package com.example.reviewerwriter.data.source

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.data.dto.GetTagDto
import com.example.reviewerwriter.data.dto.GetTokenDto
import com.example.reviewerwriter.domain.entites.PhotoFileNameEntity
import com.example.reviewerwriter.domain.entites.ReviewCardEntity
import com.example.reviewerwriter.domain.entites.ReviewDtoEntity
import com.example.reviewerwriter.domain.entites.SaveTagsEntity
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApi {
    @POST("/v1/auth/log")
    suspend fun log(@Body authDto: AuthDto): Response<GetTokenDto>
    @POST("/v1/auth/reg")
    suspend fun reg(@Body authDto: AuthDto) : Response<Unit>
    @PATCH("/v1/accounts")
    suspend fun setTags(@Header("Authorization") token: String, @Body tagDto: SaveTagsEntity): Response<Unit>
    @GET("/v1/accounts")
    suspend fun getTags(@Header("Authorization") token: String): Response<GetTagDto>
    @POST("/v1/reviews")
    suspend fun addReview(@Header("Authorization") token: String, @Body reviewDto: ReviewDtoEntity): Response<Unit>
    @POST("/v1/photos")
    suspend fun sendPhoto(@Header("Authorization") token: String, @Body photo: MultipartBody): Response<PhotoFileNameEntity>
    @GET("/v1/reviews/all")
    suspend fun getAllReviews(
        @Header("Authorization") token: String
    ): Response<List<ReviewCardEntity>>
    @GET("/v1/photos/{filename}")
    suspend fun getPhoto(
        @Header("Authorization") token: String,
        @Path("filename") filename: String
    ): Response<ByteArray>
}