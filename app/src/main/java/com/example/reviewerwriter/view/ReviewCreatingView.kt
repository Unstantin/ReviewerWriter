package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.view.ui_components.MainBottomNavView
import com.example.reviewerwriter.viewModel.ReviewCreatingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewCreatingView(context: Context, navController: NavController) {
    val reviewCreatingViewModel = remember {
        ReviewCreatingViewModel(navController)
    }
    val reviewTitle = remember { mutableStateOf("") }
    val reviewTitleFieldPlaceholder = "Название"
    val reviewDescriptionField = remember { mutableStateOf("") }
    val reviewDescriptionFieldPlaceholder = "Описание"
    var expanded by remember { mutableStateOf(false) }
    val iconList = listOf(
        "-",
        "1",
        "2",
        "3",
        "4",
        "5"
    )
    val selectedText = remember { mutableStateOf(iconList[0]) }

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(navController) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                /*TODO(): выбор фотографий */
                Spacer(
                    modifier = Modifier
                        .padding(130.dp)
                )
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextField(
                        value = reviewTitle.value,
                        onValueChange = { reviewTitle.value = it },
                        modifier = Modifier
                            .padding(15.dp)
                            .width(276.dp)
                            .clip(MaterialTheme.shapes.extraLarge),
                        placeholder = { Text(reviewTitleFieldPlaceholder) },
                        colors = TextFieldDefaults.textFieldColors(
                        ),
                        maxLines = 2,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                        //.padding(horizontal = 10.dp)
                    ) {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .wrapContentSize()
                                .fillMaxWidth()

                        ) {
                            Row {
                                Text(
                                    text = selectedText.value,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .fillMaxHeight(),
                                    style = MaterialTheme.typography.headlineLarge

                                )
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "select a rating",
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(150.dp)
                        ) {
                            iconList.forEach { icon ->
                                DropdownMenuItem(
                                    text = { Text(icon) },
                                    onClick = {
                                        expanded = false
                                        selectedText.value = icon
                                    }
                                )
                            }
                        }
                    }
                }
                TextField(
                    value = reviewDescriptionField.value,
                    onValueChange = { reviewDescriptionField.value = it },
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .fillMaxWidth()
                        .size(250.dp),
                    placeholder = { Text(reviewDescriptionFieldPlaceholder) },
                    colors = TextFieldDefaults.textFieldColors(),
                    maxLines = 15
                )
            }
        }
    }
}

@Preview
@Composable
private fun GreetingPreview() {
    val navController = rememberNavController()
    ReviewCreatingView(context = LocalContext.current, navController)
}