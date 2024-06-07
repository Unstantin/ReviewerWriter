package com.example.reviewerwriter.ui.mainScreen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel () : ViewModel() {
    //val navController = navController

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

}

