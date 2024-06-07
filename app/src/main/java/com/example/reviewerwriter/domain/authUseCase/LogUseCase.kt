package com.example.reviewerwriter.domain.authUseCase

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.entites.AuthTokenEntity
import com.example.reviewerwriter.domain.entites.Status

class LogUseCase(private val repo : AuthRepository) {
    suspend fun execute(authDto: AuthDto, callback: (Status<AuthTokenEntity>) -> Unit){
        repo.loginUser(authDto, callback)
    }
}