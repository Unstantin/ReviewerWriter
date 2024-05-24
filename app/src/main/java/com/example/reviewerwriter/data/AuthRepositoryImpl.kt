package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.etites.AuthTokenEntity
import com.example.reviewerwriter.domain.etites.Status

class AuthRepositoryImpl : AuthRepository {
    private val mainApi = RetrofitFactory.getMainApi()
    override suspend fun LoginUser(authDto: AuthDto): Status<AuthTokenEntity> {
        return try {
            val response = mainApi.log(authDto)
            if (response.token != null) {
                // Если токен пришел в ответе, создаем AuthTokenEntity и возвращаем его
                Status(200, AuthTokenEntity(response.token), null)
            } else {
                // Если токен не пришел или он пустой, возвращаем ошибку
                Status(400, null, Exception("Token not found in response"))
            }
        } catch (e: Exception) {
            // Если произошло исключение, возвращаем его
            Status(-1, null, e)
        }
    }
}