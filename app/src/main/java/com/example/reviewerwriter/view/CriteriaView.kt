package com.example.reviewerwriter.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.utils.ObserveToastMessage
import com.example.reviewerwriter.view.ui_components.MainBottomNavView
import com.example.reviewerwriter.viewModel.CriteriaViewModel
import com.example.reviewerwriter.viewModel.MainBottomNavViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriteriaView (
    criteriaViewModel: CriteriaViewModel,
    context: Context,
    navController: NavController,
    mainBottomNavViewModel: MainBottomNavViewModel
){

    val addCriteriaTextField = criteriaViewModel.addCriteriaTextField
    val addCriteriaTextFieldPlaceholder = "Добавить критерий"
    val сriteriaList = criteriaViewModel.сriteriaList

    ObserveToastMessage(criteriaViewModel, context)

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Критерии")},
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                        }
                })
            }
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                item {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .padding(8.dp)
                    ){
                        TextField(
                            value = addCriteriaTextField.value,
                            onValueChange = { if (it.length <= 25) addCriteriaTextField.value  = it},
                            modifier = Modifier
                                .padding(top = 25.dp)
                                .width(250.dp)
                                .clip(MaterialTheme.shapes.extraLarge),
                            placeholder = { Text(addCriteriaTextFieldPlaceholder) },
                            colors = TextFieldDefaults.textFieldColors(
                            ),

                            )
                        Button(
                            onClick = {
                                criteriaViewModel.addCritaria(addCriteriaTextField.value)
                                addCriteriaTextField.value =""
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)
                                .size(200.dp, 50.dp),
                        ) {
                            Text(
                                "Создать",
                            )
                        }
                    }
                }
                items(
                    criteriaViewModel.сriteriaList.value.size
                ) {
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        ,
                        ){
                        Row (
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = сriteriaList.value[it],
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                )
                            IconButton(
                                onClick = { criteriaViewModel.removeCritaria(сriteriaList.value[it])
                                },
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.shapes.small
                                    )
                                    .size(32.dp, 32.dp)
                            )
                            {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить критерий"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}