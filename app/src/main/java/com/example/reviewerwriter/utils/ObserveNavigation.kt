package com.example.reviewerwriter.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.reviewerwriter.viewModel.LoginViewModel

@Composable
fun ObserveNavigation(ViewModel: ObserveNavigationInterface, onClick: () -> Unit) {
    // отслеживание и переход между экранами
    ViewModel.navigateTo.observeAsState().value?.let { shouldNavigate ->
        if (shouldNavigate) {
            ViewModel.onNavigationDone()
            onClick()
        }
    }
}