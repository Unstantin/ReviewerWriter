package com.example.reviewerwriter.ui.chatScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.MainBottomNavView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenView(
    mainBottomNavViewModel: MainBottomNavViewModel,
    context: Context,
    navController: NavController
) {

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Данный экран находится в разработке и пока недоступен",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Разработка",
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}