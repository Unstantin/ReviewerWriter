package com.example.reviewerwriter.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ObserveNavigationInterface {
    val _navigateTo : MutableLiveData<Boolean>
    val navigateTo: LiveData<Boolean> get() = _navigateTo
    fun onNavigationDone()
    fun onTextButtonSignInClick()
}