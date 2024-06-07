package com.example.reviewerwriter.ui.reviewScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.ui_components.MainBottomNavView
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.reviewerwriter.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun ReviewView(
    reviewViewModel: ReviewViewModel,
    mainBottomNavViewModel: MainBottomNavViewModel,
    context: Context,
    navController: NavController
) {
    val reviewTitle by reviewViewModel.reviewTitle
    val reviewText by reviewViewModel.reviewText
    val criteria by reviewViewModel.criteria
    val likesN by reviewViewModel.likesN
    val selectedImageUris by reviewViewModel.selectedImageUris

    val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })

    // TODO не присылается информация о том лайкнута ли уже запись
    // TODO та же хуйня с избранным
    ReviewerWriterTheme {
        Scaffold(
            bottomBar = { MainBottomNavView(mainBottomNavViewModel, navController) },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .height(330.dp)
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small),
                        ) {
                            HorizontalPager(
                                state = pagerState
                            ) { page ->
                                Box(
                                    modifier = Modifier
                                ) {
                                    AsyncImage(
                                        model = selectedImageUris[page],
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                }

                item {
                    Text(text = reviewTitle, fontSize = 22.sp)
                    Spacer(modifier = Modifier.padding(bottom = 15.dp))
                }

                item {
                    Text(text = reviewText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                }

                item {
                    TagsFlowRow(criteria = criteria)
                    Spacer(modifier = Modifier.padding(bottom = 40.dp))
                }

                item {
                    Row {
                        LikeButtonWithColorChange(initialLikesCount = likesN, onLikeClicked = { /*TODO*/ })
                        FavoriteButton(onFavoriteClicked = { /* TODO */ })
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(onFavoriteClicked: () -> Unit) {
    val isFavorite = remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            isFavorite.value =!isFavorite.value
            onFavoriteClicked()
        },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.mipmap.star),
            contentDescription = "Favorite Icon",
            tint = if (isFavorite.value) Color.Yellow else MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun LikeButtonWithColorChange(
    initialLikesCount: Int = 0,
    onLikeClicked: (Int) -> Unit
) {
    val (likesCount, setLikesCount) = remember { mutableIntStateOf(initialLikesCount) }
    val (isLiked, setIsLiked) = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if(isLiked) {
                    setLikesCount(likesCount - 1)
                    setIsLiked(false)
                    onLikeClicked(likesCount - 1)
                } else {
                    setLikesCount(likesCount + 1)
                    setIsLiked(true)
                    onLikeClicked(likesCount + 1)
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.like),
                contentDescription = "Like Icon",
                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.surface
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = likesCount.toString()
        )
    }
}

@Composable
fun CriteriaItem(name: String, value: Int) {
    Row {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 4.dp, end = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(4.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = value.toString(),
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(4.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlowRow(criteria: Map<String, Int>, modifier: Modifier = Modifier) {
    FlowRow(
        maxItemsInEachRow = Int.MAX_VALUE,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        criteria.forEach { criteria ->
            CriteriaItem(name = criteria.key, value = criteria.value)
        }
    }
}