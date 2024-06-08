package com.example.reviewerwriter.ui.mainScreen.reviewCardView

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reviewerwriter.domain.entites.ReviewCardEntity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ReviewCardView(
    reviewCardViewModel: ReviewCardViewModel,
    reviewCard : ReviewCardEntity,
onClick: () -> Unit
) {

    val imageNames: List<String> = reviewCard.photos!!
    val pagerState = rememberPagerState(pageCount = { imageNames.size })
    val reviewTitle: String = reviewCard.title!!
    //val selectedRate: Int = 5
    val reviewDescription: String = reviewCard.shortText!!
    LaunchedEffect(key1 = imageNames) {
        reviewCardViewModel.getBitmapList(imageNames)
    }

    val bitmapList by reviewCardViewModel.bitmapList.collectAsState()

    /*val tags = mutableStateOf(listOf<String>(
        "tag2",
        "tag2"
    ))
    val criteria = mutableStateOf(listOf<String>(
        "criteria1",
        "criteria2"
    ))
*/


    Card(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                if (bitmapList.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                }
                        ) {
                            AsyncImage(
                                model = bitmapList[page],
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
            Row() {
                Text(
                    text = reviewTitle,
                    maxLines = 2
                )
                /*Row {
                    Text(
                        text = selectedRate.toString(),
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
                }*/
            }
            Text(
                text = reviewDescription,
                maxLines = 3
            )
            /*FlowRow(
            ) {
                tags.value.forEach { tag ->
                    InputChip(
                        label = {
                            Text(text = tag)
                        },
                        onClick = {},
                        selected = false,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                    )
                }
            }
            FlowRow(
            ) {
                criteria.value.forEach { criteria ->
                    InputChip(
                        label = {
                            Text(text = criteria)
                        },
                        onClick = {},
                        selected = false,
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                    )
                }
            }*/
        }
    }
}