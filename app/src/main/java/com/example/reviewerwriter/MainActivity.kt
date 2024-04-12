package com.example.reviewerwriter

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.utils.Screens
import com.example.reviewerwriter.view.LoginView
import com.example.reviewerwriter.view.RegistrationView
import com.example.reviewerwriter.view.MainView
import com.example.reviewerwriter.view.ReviewCreatingView

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val activity = this@MainActivity
            LaunchedEffect(activity) {
                activity.window.setBackgroundBlurRadius(15)
            }



            ReviewerWriterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    /*todo: если пользователь в аккаунте
                        то запускать активность главного экрана*/

                    //запуск активности экрана входа
                    NavHost(
                        navController = navController,
                        startDestination = Screens.LOGIN_SCREEN
                    ) {
                        composable(Screens.LOGIN_SCREEN) {
                            LoginView(context = this@MainActivity, navController)
                        }

                        composable(Screens.REGISTRATION_SCREEN) {
                            RegistrationView(context = this@MainActivity, navController)
                        }

                        composable(Screens.MAIN_SCREEN){
                            MainView(context = this@MainActivity, navController)
                        }
                        composable(Screens.REVIEW_CREATING_SCREEN){
                            ReviewCreatingView(context = this@MainActivity,navController)
                        }
                        /*TODO: вариантивность переходов в экраны
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