package com.example.reviewerwriter.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.authScreen.LoginView
import com.example.reviewerwriter.ui.authScreen.RegistrationView
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingView
import com.example.reviewerwriter.ui.di.appModule
import com.example.reviewerwriter.ui.mainScreen.MainView
import com.example.reviewerwriter.ui.serviceScreen.ServiceView
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaView
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsView
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.utils.Screens
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
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
                    NavHost(
                        navController = navController,
                        startDestination = Screens.LOGIN_SCREEN
                    ) {
                        composable(Screens.LOGIN_SCREEN) {
                            LoginView(
                                loginViewModel = koinViewModel(),
                                this@MainActivity,
                                navController)
                        }

                        composable(Screens.REGISTRATION_SCREEN) {
                            RegistrationView(
                                registrationViewModel = koinViewModel(),
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.MAIN_SCREEN) {
                            MainView(
                                mainViewModel = koinViewModel(),
                                mainBottomNavViewModel = koinViewModel(),
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.REVIEW_CREATING_SCREEN) {
                            ReviewCreatingView(
                                reviewCreatingViewModel = koinViewModel(),
                                tagsViewModel = koinViewModel(),
                                criteriaViewModel = koinViewModel(),
                                mainBottomNavViewModel = koinViewModel(),
                                this@MainActivity,
                                navController
                            )
                        }

                        composable(Screens.SERVICES_SCREEN){
                            ServiceView(
                                serviceViewModel = koinViewModel(),
                                tagsViewModel = koinViewModel(),
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = koinViewModel()
                            )
                        }

                        composable(Screens.TAGS_SCREEN){
                            TagsView(
                                tagsViewModel = koinViewModel(),
                                criteriaViewModel = koinViewModel(),
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = koinViewModel()
                            )
                        }
                        composable(Screens.CRITERIA_SCREEN){
                            CriteriaView(
                                criteriaViewModel = koinViewModel(),
                                context = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = koinViewModel()
                            )
                        }
                        /*composable(Screens.REVIEW_CARD_SCREEN){
                            ReviewCardView(
                                reviewCardViewModel = koinViewModel(),
                                contect = this@MainActivity,
                                navController = navController,
                                mainBottomNavViewModel = koinViewModel()
                            )
                        }*/
                    }
                }
            }
        }
    }
}