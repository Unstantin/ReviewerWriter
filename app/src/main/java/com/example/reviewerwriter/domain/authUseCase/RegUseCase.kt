package com.example.reviewerwriter.domain.authUseCase

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.entites.Status

class RegUseCase(private val repo: AuthRepository) {
    suspend fun execute(authDto: AuthDto, callback: (Status<Unit>) -> Unit){
        repo.registrationUser(authDto, callback);
    }
}