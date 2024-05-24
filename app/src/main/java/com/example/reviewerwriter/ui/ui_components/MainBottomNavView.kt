package com.example.reviewerwriter.ui.ui_components

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
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.utils.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomNavView(
    mainBottomNavViewModel: MainBottomNavViewModel,
    navController: NavController
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Домой",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        ),
        BottomNavigationItem(
            title = "Чаты",
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
            screen = Screens.REVIEW_CREATING_SCREEN
        ),
        BottomNavigationItem(
            title = "Сервисы",
            selectedIcon = Icons.Default.BackupTable,
            unselectedIcon = Icons.Outlined.BackupTable,
            hasNews = false,
            screen = Screens.SERVICES_SCREEN
        ),
        BottomNavigationItem(
            title = "Библио",
            selectedIcon = Icons.Default.Book,
            unselectedIcon = Icons.Outlined.Book,
            hasNews = false,
            screen = Screens.MAIN_SCREEN
        )
    )


    ReviewerWriterTheme {
        NavigationBar(
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = mainBottomNavViewModel.getMyCurrentScreenIndex() == index,
                    onClick = {
                        mainBottomNavViewModel.setMyCurrentScreenIndex(index)
                        mainBottomNavViewModel.onNavigationBarItemClick(item, navController)
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
                                imageVector = if (index == mainBottomNavViewModel.getMyCurrentScreenIndex()) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.title,
                            )
                        }
                    },

                    )
            }
        }
    }
}