package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewerwriter.ui.theme.Gray10
import com.example.reviewerwriter.ui.theme.Gray60
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.view.ui_components.MainBottomNavView
import com.example.reviewerwriter.viewModel.MainBottomNavViewModel
import com.example.reviewerwriter.viewModel.ReviewCreatingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReviewCreatingView(
    reviewCreatingViewModel: ReviewCreatingViewModel,
    mainBottomNavViewModel: MainBottomNavViewModel,
    context: Context,
    navController: NavController
) {

    var reviewTitle by reviewCreatingViewModel.reviewTitle
    val reviewTitleFieldPlaceholder = "Название"
    var reviewDescriptionField by reviewCreatingViewModel.reviewDescriptionField
    val reviewDescriptionFieldPlaceholder = "Описание"
    var expanded by reviewCreatingViewModel.expanded
    val iconList = reviewCreatingViewModel.iconList
    var selectedText by reviewCreatingViewModel.selectedText
    var selectedImageUris by reviewCreatingViewModel.selectedImageUris

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = selectedImageUris + uris }
    )
    val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })
   /* val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            selectedImageUris = selectedImageUris + bitmap.toUri()
        }
    )*/

    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .height(330.dp)
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small),

                    ) {
                    if (selectedImageUris.isNotEmpty()) {
                        HorizontalPager(
                            state = pagerState
                        ) { page ->
                            Box(
                                modifier = Modifier
                                    .clickable {

                                    }
                            ) {
                                AsyncImage(
                                    model = selectedImageUris[page],
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                        //.aspectRatio(3f/2f)
                                    contentScale = ContentScale.Crop
                                )
                                // удаление фотки
                                IconButton(
                                    onClick = {
                                        val currentList = selectedImageUris.toMutableList()
                                        if (page < selectedImageUris.size) {
                                            currentList.removeAt(page)
                                            selectedImageUris = currentList
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surface,
                                            MaterialTheme.shapes.small
                                        )
                                        .size(32.dp, 32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Удалить фотографию"
                                    )
                                }
                            }

                        }
                    }
                    else{
                        //пустая фотка
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    multiplePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddPhotoAlternate,
                                    contentDescription = "AddPhoto",
                                    modifier = Modifier
                                        .size(100.dp),
                                    )
                                Text(
                                    text = "Добавить фото",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }

                    if(selectedImageUris.size > 1){
                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color = if (pagerState.currentPage == iteration) Gray10 else Gray60
                                val size = if (pagerState.currentPage == iteration) 8 else 6
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(size.dp)
                                )
                            }
                        }
                    }

                }
                Row (
                    modifier = Modifier
                        .padding(15.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        Text(text = "Выбрать фото")
                    }

                    Spacer(Modifier.width(16.dp))

                    Button(
                        onClick = {
                            //cameraLauncher.launch(null)
                            /*TODO*/
                        }
                    ) {
                        Text(text = "Сделать фото")
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextField(
                        value = reviewTitle,
                        onValueChange = { reviewTitle = it },
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
                    ) {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .wrapContentSize()
                                .fillMaxWidth()

                        ) {
                            Row {
                                Text(
                                    text = selectedText,
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
                                        selectedText = icon
                                    }
                                )
                            }
                        }
                    }
                }
                TextField(
                    value = reviewDescriptionField,
                    onValueChange = { reviewDescriptionField = it },
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

/*
@Preview
@Composable
private fun GreetingPreview() {
    val navController = rememberNavController()
    ReviewCreatingView(context = LocalContext.current, navController)
}*/
