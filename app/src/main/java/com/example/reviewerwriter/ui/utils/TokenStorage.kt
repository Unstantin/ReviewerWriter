package com.example.reviewerwriter.ui.utils

import android.content.Context
import android.content.SharedPreferences

class TokenStorage(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}