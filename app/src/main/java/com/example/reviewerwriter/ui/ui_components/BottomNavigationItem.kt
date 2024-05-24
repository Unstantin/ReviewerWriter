package com.example.reviewerwriter.ui.ui_components

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val screen: String
)
