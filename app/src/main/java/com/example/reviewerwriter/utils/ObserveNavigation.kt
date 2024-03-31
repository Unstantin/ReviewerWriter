package com.example.reviewerwriter.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.reviewerwriter.viewModel.LoginViewModel
import java.util.Objects

@Composable
    fun ObserveNavigation(ViewModel: ObserveNavigationInterface, onNavigateTo: (route: String) -> Unit) {
        // отслеживание и переход между экранами
        ViewModel.navigateTo.observeAsState().value?.let { shouldNavigate ->
            if (shouldNavigate.isNotEmpty()) {
                ViewModel.onNavigationDone()
                onNavigateTo(shouldNavigate)
            }
        }
    }