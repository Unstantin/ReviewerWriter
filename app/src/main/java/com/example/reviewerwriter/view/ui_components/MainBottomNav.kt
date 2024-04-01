package com.example.reviewerwriter.view.ui_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BackupTable
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.BackupTable
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.utils.Screens
import com.example.reviewerwriter.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomNav(mainViewModel: MainViewModel) {

    val items = listOf(
        BottomNavigationItem(
            title = "Домой",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        ),
        BottomNavigationItem(
            title = "чаты",
            selectedIcon = Icons.Default.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        ),
        BottomNavigationItem(
            title = "Создать",
            selectedIcon = Icons.Default.AddCircle,
            unselectedIcon = Icons.Outlined.AddCircle,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        ),
        BottomNavigationItem(
            title = "Сервисы",
            selectedIcon = Icons.Default.BackupTable,
            unselectedIcon = Icons.Outlined.BackupTable,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        ),
        BottomNavigationItem(
            title = "Библио",
            selectedIcon = Icons.Default.Book,
            unselectedIcon = Icons.Outlined.Book,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        )
    )

    val selectedItemIndex = remember {
        mutableStateOf(0)
    }
    ReviewerWriterTheme {
        NavigationBar(
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex.value == index,
                    onClick = {
                        selectedItemIndex.value = index
                        mainViewModel.onNavigationBarItemClick(item)
                    },
                    label = {
                        Text(
                            item.title,
                        )
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(
                                            text = item.badgeCount.toString(),
                                        )
                                    }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selectedItemIndex.value) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.title,
                            )
                        }
                    }
                )
            }
        }
    }
}