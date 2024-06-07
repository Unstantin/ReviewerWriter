package com.example.reviewerwriter.domain.authUseCase

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.entites.AuthTokenEntity
import com.example.reviewerwriter.domain.entites.Status

interface AuthRepository {
    suspend fun loginUser(authDto: AuthDto, callback: (Status<AuthTokenEntity>) -> Unit): Unit
    suspend fun registrationUser(authDto: AuthDto, callback: (Status<Unit>) -> Unit): Unit
}