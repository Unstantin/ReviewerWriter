package com.example.reviewerwriter.ui.serviceScreen.tags

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.MainBottomNavView
import com.example.reviewerwriter.ui.utils.ObserveToastMessage
import com.example.reviewerwriter.ui.utils.Screens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagsView (tagsViewModel: TagsViewModel,
              criteriaViewModel: CriteriaViewModel,
              context: Context,
              navController: NavController,
              mainBottomNavViewModel: MainBottomNavViewModel
){
    val addTagTextField = tagsViewModel.addTagTextField
    val addTagTextFieldPlaceholder = "Добавить тег"
    val criteriaList = criteriaViewModel.сriteriaList
    val expanded = tagsViewModel.expanded
    val selectedCriteria = tagsViewModel.selectedCriteria
    val mapTagsCriteria = tagsViewModel.mapTagsCriteria

    ObserveToastMessage(tagsViewModel, context)

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Теги") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                        }
                    }
                )
            }
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {

                /*Создание тегов*/
                item {
                    Column {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .padding(8.dp)
                        ){
                            TextField(
                                value = addTagTextField.value,
                                onValueChange = { if (it.length <= 25) addTagTextField.value  = it},
                                modifier = Modifier
                                    .padding(top = 25.dp)
                                    .width(250.dp)
                                    .clip(MaterialTheme.shapes.extraLarge),
                                placeholder = { Text(addTagTextFieldPlaceholder) },
                                colors = TextFieldDefaults.textFieldColors(
                                ),
                            )
                            Button(
                                onClick = {
                                    tagsViewModel.addTagCriteria(
                                        addTagTextField.value,
                                        selectedCriteria.value)
                                    addTagTextField.value =""
                                    selectedCriteria.value = emptyList()
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
                        Row (
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { expanded.value = true },
                        ){
                            Text(
                                text = "Добавить критерии:",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Добавить критерии",
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier
                                .width(150.dp)
                        ) {
                            if(criteriaList.value.isNotEmpty()) {
                                criteriaList.value.forEach { criteria ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = rememberRipple(),
                                                onClick = {
                                                    if (selectedCriteria.value.contains(criteria)) {
                                                        tagsViewModel.removeC(criteria)
                                                        //selectedCriteria.remove(criteria)
                                                    } else {
                                                        tagsViewModel.addC(criteria)
                                                        //selectedCriteria.value.add(criteria)
                                                    }
                                                    //expanded.value = false
                                                }
                                            )
                                            .padding(16.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = selectedCriteria.value.contains(criteria),
                                                onCheckedChange = null // null recommended for accessibility with screenreaders
                                            )
                                            Text(
                                                text = criteria,
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            else{
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Нет созданных критериев, нажмите чтобы добавить",
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clickable {
                                                navController.navigate(Screens.CRITERIA_SCREEN)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }

                /*Выбранные критерии*/
                item{
                    FlowRow(
                        /*mainAxisSpacing = 8.dp, // расстояние по главной оси
                        crossAxisSpacing = 8.dp  // расстояние по поперечной оси*/
                    ) {
                        selectedCriteria.value.forEach { criteria ->
                            InputChip(
                                selected = true,
                                onClick = { tagsViewModel.removeC(criteria) },
                                label = {
                                    Text(text = criteria)
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "убрать критерий"
                                    )
                                },
                                modifier = Modifier
                                    .padding(horizontal = 2.dp),

                            )
                        }
                    }
                }

                /*Созданные теги*/
                itemsIndexed(
                    items = tagsViewModel.mapTagsCriteria.value.keys.toList()
                ) { index, key ->
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                        ) {
                            Column {
                                Row (
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = key,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    )
                                    IconButton(
                                        onClick = { tagsViewModel.removeTagCriteria(key) },
                                        modifier = Modifier
                                            .background(
                                                MaterialTheme.colorScheme.surface,
                                                MaterialTheme.shapes.small
                                            )
                                            .size(32.dp, 32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Удалить тэг"
                                        )
                                    }
                                }
                                FlowRow() {
                                    mapTagsCriteria.value[key]?.forEach { criteria ->
                                        InputChip(
                                            selected = true,
                                            onClick = {
                                                tagsViewModel.removeValueFromTagCriteria(key,criteria)
                                            },
                                            label = {
                                                Text(text = criteria)
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "убрать критерий"
                                                )
                                            },
                                            modifier = Modifier
                                                .padding(horizontal = 2.dp),
                                        )
                                    }
                                }
                            }
                        }

                }
            }
        }
    }
}