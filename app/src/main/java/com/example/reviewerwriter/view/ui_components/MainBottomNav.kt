package com.example.reviewerwriter.view.ui_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.ui.graphics.Color
import com.example.reviewerwriter.ui.theme.DarkMuted
import com.example.reviewerwriter.ui.theme.DarkVibrant
import com.example.reviewerwriter.ui.theme.LightVibrant
import com.example.reviewerwriter.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomNav(mainViewModel: MainViewModel) {
    /*BottomAppBar(
        containerColor = DarkMuted,
        contentColor = Vibrant
    ) {
        *//*TODO: нижние элементы*//*
    }*/
    val items = listOf(
        BottomNavigationItem(
            title = "Домой",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "чаты",
            selectedIcon = Icons.Default.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Создать",
            selectedIcon = Icons.Default.AddCircle,
            unselectedIcon = Icons.Outlined.AddCircle,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Сервисы",
            selectedIcon = Icons.Default.Lock,
            unselectedIcon = Icons.Outlined.Lock,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Библиотека",
            selectedIcon = Icons.Default.List,
            unselectedIcon = Icons.Outlined.List,
            hasNews = false
        )
    )

    var selectedItemIndex = remember {
        mutableStateOf(0)
    }
    NavigationBar(
        containerColor = DarkMuted,
        contentColor = LightVibrant
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex.value == index,
                onClick = {
                    selectedItemIndex.value = index
                    //navController.navigate(item.title)
                    /*TODO*/
                },
                label = {
                        Text(item.title, color = if (selectedItemIndex.value == index) LightVibrant else DarkVibrant)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if(item.badgeCount != null){
                                Badge {
                                    Text(text = item.badgeCount.toString(), color = if (selectedItemIndex.value == index) LightVibrant else DarkVibrant)
                                }
                            } else if(item.hasNews){
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
                            tint = if (selectedItemIndex.value == index) Color.Black else DarkVibrant
                        )
                    }
                }
            )
        }
    }
}