package com.example.reviewerwriter.ui.ui_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.mainScreen.MainViewModel
import com.example.reviewerwriter.ui.mainScreen.reviewCardView.ReviewCardView
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    mainViewModel: MainViewModel,
    mainBottomNavViewModel: MainBottomNavViewModel,
    drawerState: DrawerState,
    navController: NavController
){
    ReviewerWriterTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth(),
            topBar = {
                MainTopBar(mainViewModel, drawerState)
            },
            bottomBar = {
                MainBottomNavView(mainBottomNavViewModel,navController)
            }
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                items(mainViewModel.reviewCards.value.size) {
                    ReviewCardView(
                        reviewCardViewModel = koinViewModel(),
                        mainViewModel.reviewCards.value[it],
                        onClick = { /* Обработка клика на карточку отзыва */ }
                    )
                }
            }
        }
    }
}
