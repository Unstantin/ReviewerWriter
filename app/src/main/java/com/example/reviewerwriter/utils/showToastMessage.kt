package com.example.reviewerwriter.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface showToastMessage {

    val _showToastMessage: MutableLiveData<String>
    val showToastMessage: LiveData<String> get() = _showToastMessage
    fun onshowToastMessageDone()
}