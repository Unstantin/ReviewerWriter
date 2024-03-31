package com.example.reviewerwriter.viewModel

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.reviewerwriter.view.ui_components.BottomNavigationItem

class MainViewModel (navController: NavController){
    val navController = navController

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    fun onNavigationBarItemClick(item: BottomNavigationItem){
        navController.navigate(item.screen)
    }
}