package com.example.reviewerwriter.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ObserveToastMessage(ViewModel : showToastMessage, context: Context) {
    // отслеживание и вывод ошибок
    ViewModel.showToastMessage.observeAsState().value?.let { ToastMessage ->
        if(ToastMessage.trim().isNotEmpty()) {
            Toast.makeText(context, ToastMessage, Toast.LENGTH_SHORT).show()
            ViewModel.onshowToastMessageDone()
        }
    }
}