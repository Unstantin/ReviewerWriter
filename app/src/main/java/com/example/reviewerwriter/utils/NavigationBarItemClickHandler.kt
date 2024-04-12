package com.example.reviewerwriter.utils

import com.example.reviewerwriter.view.ui_components.BottomNavigationItem

interface NavigationBarItemClickHandler {
    fun onNavigationBarItemClick(item: BottomNavigationItem)
    fun getMyCurrentScreenIndex(): Int
    fun setMyCurrentScreenIndex(index: Int)
    var currentScreenIndex: Int
}