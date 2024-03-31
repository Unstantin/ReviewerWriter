package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reviewerwriter.ui.theme.Muted
import com.example.reviewerwriter.ui.theme.Vibrant
import com.example.reviewerwriter.view.ui_components.DrawerItem
import com.example.reviewerwriter.view.ui_components.MyScaffold
import com.example.reviewerwriter.viewModel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(context : Context){
    val mainViewModel = remember {
        MainViewModel()
    }
    val items = listOf(
        DrawerItem(
            Icons.Default.Settings,
            title = "Настройки"
        ),
        DrawerItem(
            Icons.Default.Add,
            title = "Пригласить"
        ),
        DrawerItem(
            Icons.Default.Info,
            title = "Помощь"
        ),
        DrawerItem(
            Icons.Default.ExitToApp,
            title = "Выход"
        )
    )
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = mainViewModel.drawerState.value)

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.7f),
        modifier = Modifier
            .background(Muted)
            .fillMaxWidth(),

        drawerContent = {
            ModalDrawerSheet(

                modifier = Modifier
                    .background(Muted)
                    .requiredWidth(300.dp)
                    .fillMaxWidth()
            ) {
                /*TODO: добавить информацию о пользователе и мб фото профиля на фон */
                /*TODO: разобраться с цветом фона*/
                Spacer(modifier = Modifier.height(150.dp).background(Muted))
                items.forEach { item ->
                    NavigationDrawerItem(
                        modifier = Modifier
                            .background(Muted),
                        icon = {
                               Icon(
                                   imageVector = item.image,
                                   contentDescription = item.title,
                                   tint = Vibrant,
                                   modifier = Modifier.background(Muted)
                               )
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = Vibrant,
                                modifier = Modifier.background(Muted)
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }

                            /*TODO: перенести в model*/
                        }
                    )
                }
            }
        },
        content = {
            MyScaffold(mainViewModel = mainViewModel, drawerState)
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    MainView(context = LocalContext.current)

}
