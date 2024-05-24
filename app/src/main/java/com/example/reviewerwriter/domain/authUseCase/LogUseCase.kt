package com.example.reviewerwriter.domain.authUseCase

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.etites.AuthTokenEntity
import com.example.reviewerwriter.domain.etites.Status

class LogUseCase(private val repo : AuthRepository) {
    fun execute(authDto: AuthDto, callback: (Status<AuthTokenEntity>) -> Unit){
       // repo.LoginUser(authDto, callback)

    }
}