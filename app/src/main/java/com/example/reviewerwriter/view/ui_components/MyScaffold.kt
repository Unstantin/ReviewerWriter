package com.example.reviewerwriter.view.ui_components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.reviewerwriter.ui.theme.Muted
import com.example.reviewerwriter.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScaffold(mainViewModel : MainViewModel, drawerState: DrawerState){
    Scaffold(
        modifier = Modifier
            .background(Muted)
            .fillMaxWidth(),
        topBar = {
            MainTopBar(mainViewModel,drawerState)
        },
        bottomBar = {
            MainBottomNav(mainViewModel)
        }
    ) {
        /*TODO: основное порстранство*/
        Text(text = "Привет мир!")
    }
}