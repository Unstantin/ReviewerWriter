package com.example.reviewerwriter.retrofit

import com.example.reviewerwriter.model.LoginRequest
import com.example.reviewerwriter.model.LoginResponses
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApi {
    @POST("/v1/auth/log")
    suspend fun authentication(@Body loginRequest: LoginRequest): LoginResponses
}