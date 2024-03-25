package com.example.reviewerwriter.retrofit

import com.example.reviewerwriter.model.LoginRequest
import com.example.reviewerwriter.model.LoginResponses
import com.example.reviewerwriter.model.RegistrationRequest
import com.example.reviewerwriter.model.RegistrationResponses
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApi {
    @POST("/v1/auth/log")
    suspend fun authentication(@Body loginRequest: LoginRequest): LoginResponses
    @POST("/v1/auth/reg")
    suspend fun registration(@Body registrationRequest: RegistrationRequest) : RegistrationResponses
}