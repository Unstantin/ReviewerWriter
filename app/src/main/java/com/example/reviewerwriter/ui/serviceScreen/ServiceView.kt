package com.example.reviewerwriter.ui.serviceScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.MainBottomNavView
import com.example.reviewerwriter.ui.utils.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceView (
    serviceViewModel: ServiceViewModel,
    tagsViewModel: TagsViewModel,
    context: Context,
    navController: NavController,
    mainBottomNavViewModel: MainBottomNavViewModel
) {

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Сервисы")})
            }
        ) {values ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                            ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .width(180.dp)
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            .padding(8.dp)
                            .clickable {
                                       navController.navigate(Screens.TAGS_SCREEN)
                            },


                    ){
                        Column {
                            Text(
                                text = "Теги",
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                color = MaterialTheme.colorScheme.onPrimary

                            )
                            Text(
                                text = "управление тегами",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .width(180.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                            .clickable {
                                      navController.navigate(Screens.CRITERIA_SCREEN)
                                //navController.navigate(Screens.TAGS_SCREEN)
                            },
                    ){
                        Column {
                            Text(
                                text = "Критерии",
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                color = MaterialTheme.colorScheme.onPrimary

                            )
                            Text(
                                text = "управление критериями",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                    }
                }
            }
        }
    }
}