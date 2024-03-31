package com.example.reviewerwriter.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Objects

interface ObserveNavigationInterface {
    val _navigateTo : MutableLiveData<String>
    val navigateTo: LiveData<String> get() = _navigateTo
    fun onNavigationDone()
    fun onTextButtonSignInClick()
}