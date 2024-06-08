package com.example.reviewerwriter.ui.createScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.serviceScreen.criteria.CameraPreviewView
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaViewModel
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsViewModel
import com.example.reviewerwriter.ui.theme.Gray10
import com.example.reviewerwriter.ui.theme.Gray60
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.MainBottomNavView
import com.example.reviewerwriter.ui.utils.ObserveToastMessage
import com.example.reviewerwriter.ui.utils.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ReviewCreatingView(
    reviewCreatingViewModel: ReviewCreatingViewModel,
    tagsViewModel: TagsViewModel,
    criteriaViewModel: CriteriaViewModel,
    mainBottomNavViewModel: MainBottomNavViewModel,
    context: Context,
    navController: NavController
) {

    var reviewTitle by reviewCreatingViewModel.reviewTitle
    val reviewTitleFieldPlaceholder = "Название"
    var reviewDescriptionField by reviewCreatingViewModel.reviewDescriptionField
    val reviewDescriptionFieldPlaceholder = "Описание"
    var selectedImage by reviewCreatingViewModel.selectedImage
    val mapTagsCriteria = tagsViewModel.mapTagsCriteria
    val expandedTag =  reviewCreatingViewModel.expandedTag
    val expandedSelectStar = reviewCreatingViewModel.expandedSelectStar
    val onSelectedCriteria = reviewCreatingViewModel.onSelectedCriteria
    val onSelectedTag = reviewCreatingViewModel.onSelectedTag
    val tags = reviewCreatingViewModel.tags
    val showCameraPreview = reviewCreatingViewModel.showCameraPreview
    var mainRate = reviewCreatingViewModel.mainRate
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            // Обновление selectedImageUris в ReviewCreatingViewModel
            uris?.let {
                reviewCreatingViewModel.addImageUris(it, context)
            }
        }
    )
    val pagerState = rememberPagerState(pageCount = { selectedImage.size })
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    val cameraPermissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Разрешение предоставлено, открываем экран с камерой
                showCameraPreview.value = true
            } else {
                // Разрешение не предоставлено, показываем сообщение об ошибке или информацию о том, что разрешение необходимо
            }
        }
    )
    //отслеживание
    ObserveToastMessage(reviewCreatingViewModel, context)

    ReviewerWriterTheme {
        CenterAlignedTopAppBar(
            title = { Text(text = "Создать рецензию") }
        )
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        reviewCreatingViewModel.onButtonSaveClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                    )
                }
            }
        ) { values ->
            if (showCameraPreview.value) {
                CameraPreviewView(
                    reviewCreatingViewModel,
                    context = context,
                    controller = controller,
                    modifier = Modifier.fillMaxSize()
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .height(330.dp)
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small),

                        ) {
                        if (selectedImage.isNotEmpty()) {
                            HorizontalPager(
                                state = pagerState
                            ) { page ->
                                Box(
                                    modifier = Modifier
                                        .clickable {

                                        }
                                ) {
                                    AsyncImage(
                                        model = selectedImage[page],
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
                                            val currentList = selectedImage.toMutableList()
                                            if (page < selectedImage.size) {
                                                currentList.removeAt(page)
                                                selectedImage = currentList
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
                        } else {
                            //пустая фотка
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        multiplePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                            ) {
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

                        if (selectedImage.size > 1) {
                            Row(
                                Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(pagerState.pageCount) { iteration ->
                                    val color =
                                        if (pagerState.currentPage == iteration) Gray10 else Gray60
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
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
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
                                // Проверяем разрешение на использование камеры
                                when {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED -> {
                                        // Разрешение уже предоставлено, открываем экран с камерой
                                        showCameraPreview.value = true
                                    }
                                    else -> {
                                        // Запрашиваем разрешение
                                        cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            }
                        ) {
                            Text(text = "Сделать снимок")
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
                                onClick = { /*expandedStar = true*/ },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .fillMaxWidth()

                            ) {
                                Row {
                                    Text(
                                        text = mainRate.value,
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
                item {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { expandedTag.value = true },
                    ) {
                        Text(
                            text = "Добавить теги:",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить тег",
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedTag.value,
                        onDismissRequest = { expandedTag.value = false },
                        modifier = Modifier
                            .width(150.dp)
                    ) {
                        if(mapTagsCriteria.value.isNotEmpty()) {

                            mapTagsCriteria.value.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(),
                                            onClick = {
                                                if (tags.value.any { it.name == tag.key }) {
                                                    reviewCreatingViewModel.removeTag(
                                                        tag.key
                                                    )
                                                } else {
                                                    reviewCreatingViewModel.addTag(
                                                        tag.key,
                                                        mapTagsCriteria.value
                                                    )

                                                }
                                            }
                                        )
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = tags.value.any { it.name == tag.key },
                                            onCheckedChange = null
                                        )
                                        Text(
                                            text = tag.key,
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
                                    text = "Нет созданных тегов, нажмите чтобы добавить",
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .clickable {
                                            navController.navigate(Screens.TAGS_SCREEN)
                                        }
                                )
                            }
                        }
                    }
                }
                /*Созданные теги*/
                itemsIndexed(
                    items = tags.value.map { it.name }.toList()
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
                                    onClick = { reviewCreatingViewModel.removeTag(key) },
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.surface,
                                            MaterialTheme.shapes.small
                                        )
                                        .size(32.dp, 32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Удалить тэг"
                                    )
                                }
                            }

                            FlowRow(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                            ) {
                                // Находим тег, соответствующий key
                                val currentTag = tags.value.find { it.name == key }
                                // Проходим по критериям тега
                                currentTag?.criteria?.forEach { criteria ->
                                    InputChip(
                                        selected = currentTag.criteria?.any { it.name == criteria.name } ?: false,
                                        onClick = {
                                            onSelectedCriteria.value = criteria.name.toString()
                                            onSelectedTag.value = currentTag.name.toString()
                                            expandedSelectStar.value = true
                                        },
                                        label = {
                                            Text(
                                                text = "${criteria.name}",
                                                modifier = Modifier.padding(end = 4.dp)
                                            )
                                            Text(
                                                text = "${criteria.value}",
                                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                modifier = Modifier.padding(end = 0.dp)
                                            )
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "рейтинг",
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp)
                                    )
                                }
                            }

                        }
                    }
                    /* Выбор рейтинга */
                    DropdownMenu(
                        expanded = expandedSelectStar.value,
                        onDismissRequest = { expandedSelectStar.value = false },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    ) {
                        (1..5).forEach { rating ->
                            DropdownMenuItem(
                                text = { Text(text = rating.toString()) },
                                onClick = {
                                    // Обновляем рейтинг внутри тега и критерия
                                    reviewCreatingViewModel.updateCriteriaRating(onSelectedTag.value, onSelectedCriteria.value, rating)
                                    expandedSelectStar.value = false
                                })
                        }
                        DropdownMenuItem(
                            text = { Text(text = "Удалить") },
                            onClick = {
                                // Удаляем критерий из тега
                                reviewCreatingViewModel.removeCriteriaFromTag(onSelectedTag.value, onSelectedCriteria.value)
                                expandedSelectStar.value = false
                            })
                    }
                }
                item { 
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}
