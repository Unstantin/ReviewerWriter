package com.example.reviewerwriter.data.source

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.data.dto.TagDto
import com.example.reviewerwriter.data.dto.getTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface MainApi {
    @POST("/v1/auth/log")
    suspend fun log(@Body authDto: AuthDto): getTokenDto
    @POST("/v1/auth/reg")
    suspend fun reg(@Body authDto: AuthDto) : Response<Unit>
    @PATCH("/v1/accounts")
    suspend fun setTags(@Body tagDto: TagDto): Response<Unit>

}