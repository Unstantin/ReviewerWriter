package com.example.reviewerwriter.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ObserveToastMessage(viewModel : showToastMessage, context: Context) {
    // отслеживание и вывод ошибок
    viewModel.showToastMessage.observeAsState().value?.let { toastMessage ->
        if(toastMessage.trim().isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.onshowToastMessageDone()
        }
    }
}