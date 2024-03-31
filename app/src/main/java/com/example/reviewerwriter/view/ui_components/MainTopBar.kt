package com.example.reviewerwriter.view.ui_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.reviewerwriter.ui.theme.DarkMuted
import com.example.reviewerwriter.ui.theme.Vibrant
import com.example.reviewerwriter.viewModel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(mainViewModel : MainViewModel, drawerState: DrawerState){
    val scope = rememberCoroutineScope()
    //val drawerState = rememberDrawerState(initialValue = mainViewModel.drawerState.value)
    TopAppBar(
        title = { /*TODO*/ },
        navigationIcon = {
             IconButton(
                 onClick = {
                     scope.launch {
                         drawerState.open()
                     }
                         //mainViewModel.onNavigationButtonClick(drawerState)
                }
             ) 
             {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                )
             }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = DarkMuted,
            titleContentColor = Vibrant,
            navigationIconContentColor = Vibrant
        ),
    )

}