package com.example.reviewerwriter.view.ui_components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.theme.Muted
import com.example.reviewerwriter.ui.theme.Vibrant
import com.example.reviewerwriter.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(mainViewModel: MainViewModel, drawerState: DrawerState){
    Scaffold(
        modifier = Modifier
            .background(Muted)
            .fillMaxWidth(),
            //.nestedScroll(scrollBehavior.nestedScrollConnection)
        topBar = {
            MainTopBar(mainViewModel,drawerState)
        },
        bottomBar = {
            MainBottomNav(mainViewModel)
        }
    ) {values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Muted)
                .padding(values)
        ) {
            items(100){
                Text(
                    text = "Item$it",
                    modifier = Modifier.padding(16.dp),
                    color = Vibrant
                )
            }
            //Text(text = "Hello")
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun GreetingPreview() {
    val fdrawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)
    val drawerState = rememberDrawerState(initialValue = fdrawerState.value)
    val navController = rememberNavController()
    MyScaffold(MainViewModel(navController), drawerState )
}

