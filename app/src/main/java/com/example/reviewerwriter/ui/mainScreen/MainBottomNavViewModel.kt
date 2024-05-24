package com.example.reviewerwriter.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.ui_components.BottomNavigationItem

class MainBottomNavViewModel() : ViewModel() {
    //val navController = navController

    private var currentScreenIndex: Int = 0
    fun onNavigationBarItemClick(item: BottomNavigationItem, navController: NavController) {
        navController.navigate(item.screen)
    }

    fun getMyCurrentScreenIndex(): Int {
        return currentScreenIndex
    }

    fun setMyCurrentScreenIndex(index: Int) {
        currentScreenIndex = index
    }
}