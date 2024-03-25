package com.example.reviewerwriter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.utils.Screens
import com.example.reviewerwriter.view.LoginView
import com.example.reviewerwriter.view.RegistrationView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ReviewerWriterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*todo: если пользователь в аккаунте
                        то запускать активность главного экрана*/

                    //запуск активности экрана входа
                    NavHost(
                        navController = navController,
                        startDestination = Screens.LOGIN_SCREEN
                    ) {
                        composable(Screens.LOGIN_SCREEN) {
                            LoginView(context = this@MainActivity){
                                navController.navigate(Screens.REGISTRATION_SCREEN)
                            }
                        }
                        composable(Screens.REGISTRATION_SCREEN) {
                            RegistrationView(context = this@MainActivity){
                                navController.navigate(Screens.LOGIN_SCREEN){
                                    // очистить весь стек экранов кроме указанного (LOGIN_SCREEN)
                                    popUpTo(Screens.LOGIN_SCREEN){
                                        // удаляет из стека так же указанный экран
                                        // благодаря чему при нажатии кнопки "назад" приложение закроется
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        /*todo: вариантивность переходов в экраны
                           (один экран имеет возможность открыть несколько других)*/
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReviewerWriterTheme {
    }
}