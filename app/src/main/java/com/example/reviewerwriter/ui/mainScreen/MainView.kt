    package com.example.reviewerwriter.ui.mainScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.DrawerItem
import com.example.reviewerwriter.ui.ui_components.MyScaffold
import com.example.reviewerwriter.ui.utils.ObserveToastMessage
import com.example.reviewerwriter.ui.utils.Screens
import kotlinx.coroutines.launch

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    mainViewModel : MainViewModel,
    mainBottomNavViewModel: MainBottomNavViewModel,
    context: Context,
    navController: NavController
) {

    val items = listOf(
        /*DrawerItem(
            Icons.Default.Settings,
            title = "Настройки",
            screen = ""
        ),
        DrawerItem(
            Icons.Default.Add,
            title = "Пригласить",
            screen = ""
        ),
        DrawerItem(
            Icons.Default.Info,
            title = "Помощь",
            screen = ""
        ),*/
        DrawerItem(
            Icons.Default.ExitToApp,
            title = "Выход",
            screen = Screens.LOGIN_SCREEN
        )
    )
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = mainViewModel.drawerState.value)
    ObserveToastMessage(mainViewModel, context)

    ReviewerWriterTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            scrimColor = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth(),

            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .requiredWidth(300.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(150.dp)
                    )
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = item.image,
                                    contentDescription = item.title,
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                )
                            },
                            selected = false,
                            onClick = {
                                navController.navigate(item.screen)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            },
            content = {
                MyScaffold(mainViewModel,mainBottomNavViewModel, drawerState, navController)
            }
        )
    }
}
