package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.etites.AuthTokenEntity
import com.example.reviewerwriter.domain.etites.Status
import com.example.reviewerwriter.ui.utils.TokenStorage
import com.google.gson.Gson

class AuthRepositoryImpl(private val tokenStorage: TokenStorage) : AuthRepository {
    private val mainApi = RetrofitFactory.getMainApi()
    private val gson = Gson()

    override suspend fun loginUser(authDto: AuthDto,callback: (Status<AuthTokenEntity>) -> Unit) {
        return try {
            val response = mainApi.log(authDto)
            if (response.isSuccessful) {
                response.body()?.token
                //val jsonString = response.body()?.toString() // Преобразование тела ответа в строку
                //val getTokenDto = gson.fromJson(jsonString, GetTokenDto::class.java)
                if(response.body()?.token != null){
                    // Сохранение токена в SharedPreferences
                    tokenStorage.saveToken(response.body()?.token!!)

                    callback(Status(response.code(), AuthTokenEntity(response.body()?.token!!), null))
                } else {
                    callback(Status(response.code(), null, Exception(response.errorBody()?.string())))
                }
            } else {
                callback(Status(response.code(), null, Exception(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            callback(Status(-1, null, e))
        }
    }

    override suspend fun registrationUser(authDto: AuthDto, callback: (Status<Unit>) -> Unit) {
        return try{
            val response = mainApi.reg(authDto)
            if(response.isSuccessful){
                callback(Status(response.code(),null,null))
            }
            else{
                callback(Status(response.code(),null,  Exception(response.errorBody()?.string())))
            }
        }
        catch (e: Exception){
            callback(Status(-1,null,e))
        }
    }
}