package com.example.reviewerwriter.data.network

import com.example.reviewerwriter.data.source.MainApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://91.224.87.134:4040")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    fun getMainApi(): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}