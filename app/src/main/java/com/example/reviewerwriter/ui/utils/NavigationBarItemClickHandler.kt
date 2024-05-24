package com.example.reviewerwriter.ui.utils

import com.example.reviewerwriter.ui.ui_components.BottomNavigationItem

interface NavigationBarItemClickHandler {
    fun onNavigationBarItemClick(item: BottomNavigationItem)
    fun getMyCurrentScreenIndex(): Int
    fun setMyCurrentScreenIndex(index: Int)
    var currentScreenIndex: Int
}