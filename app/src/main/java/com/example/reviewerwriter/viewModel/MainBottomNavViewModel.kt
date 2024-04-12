package com.example.reviewerwriter.viewModel

import androidx.navigation.NavController
import com.example.reviewerwriter.view.ui_components.BottomNavigationItem

class MainBottomNavViewModel(navController: NavController) {
    val navController = navController

    private var currentScreenIndex: Int = 0
    fun onNavigationBarItemClick(item: BottomNavigationItem){
        navController.navigate(item.screen)
    }
    fun getMyCurrentScreenIndex(): Int{
        return currentScreenIndex
    }
    fun setMyCurrentScreenIndex(index: Int){
        currentScreenIndex = index
    }
}