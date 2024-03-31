package com.example.reviewerwriter.viewModel

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.reviewerwriter.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel {

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    @OptIn(ExperimentalMaterial3Api::class)
    fun onNavigationButtonClick(drawerState : DrawerState){
        /*TODO*/
        CoroutineScope(Dispatchers.IO).launch {
            drawerState.open()
        }
    }
}