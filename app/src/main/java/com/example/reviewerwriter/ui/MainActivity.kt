package com.example.reviewerwriter.ui

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.authScreen.LoginView
import com.example.reviewerwriter.ui.authScreen.LoginViewModel
import com.example.reviewerwriter.ui.authScreen.RegistrationView
import com.example.reviewerwriter.ui.authScreen.RegistrationViewModel
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingView
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingViewModel
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.mainScreen.MainView
import com.example.reviewerwriter.ui.mainScreen.MainViewModel
import com.example.reviewerwriter.ui.serviceScreen.ServiceView
import com.example.reviewerwriter.ui.serviceScreen.ServiceViewModel
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaView
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaViewModel
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsView
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.utils.Screens

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
            // объекты viewModel
            val loginViewModel: LoginViewModel = viewModel()
            val registrationViewModel: RegistrationViewModel = viewModel()
            val mainBottomNavViewModel: MainBottomNavViewModel = viewModel()
            val mainViewModel: MainViewModel = viewModel()
            val reviewCreatingViewModel: ReviewCreatingViewModel = viewModel()
            val serviceViewModel: ServiceViewModel = viewModel()
            val tagsViewModel: TagsViewModel = viewModel()
            val criteriaViewModel: CriteriaViewModel = viewModel()

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
                        startDestination = Screens.MAIN_SCREEN
                    ) {
                        composable(Screens.LOGIN_SCREEN) {
                            LoginView(loginViewModel, this@MainActivity, navController)
                        }

                        composable(Screens.REGISTRATION_SCREEN) {
                            RegistrationView(
                                registrationViewModel,
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.MAIN_SCREEN) {
                            MainView(
                                mainViewModel,
                                mainBottomNavViewModel,
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.REVIEW_CREATING_SCREEN) {
                            ReviewCreatingView(
                                reviewCreatingViewModel,
                                tagsViewModel,
                                criteriaViewModel,
                                mainBottomNavViewModel,
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.SERVICES_SCREEN){
                            ServiceView(
                                serviceViewModel = serviceViewModel,
                                tagsViewModel,
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = mainBottomNavViewModel
                            )
                        }

                        composable(Screens.TAGS_SCREEN){
                            TagsView(
                                tagsViewModel = tagsViewModel,
                                criteriaViewModel = criteriaViewModel,
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = mainBottomNavViewModel
                            )
                        }
                        composable(Screens.CRITERIA_SCREEN){
                            CriteriaView(
                                criteriaViewModel = criteriaViewModel,
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = mainBottomNavViewModel
                            )
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