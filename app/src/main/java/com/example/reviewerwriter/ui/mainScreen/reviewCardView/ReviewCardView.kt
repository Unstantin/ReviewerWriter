package com.example.reviewerwriter.ui.mainScreen.reviewCardView

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ReviewCardView(
onClick: () -> Unit
) {

    val imageUris: List<String> = mutableListOf()
    val pagerState = rememberPagerState(pageCount = { imageUris.size })
    val reviewTitle: String = "Название"
    val selectedRate: Int = 5
    val reviewDescription: String = "Описание"
    val tags = mutableStateOf(listOf<String>(
        "tag2",
        "tag2"
    ))
    val criteria = mutableStateOf(listOf<String>(
        "criteria1",
        "criteria2"
    ))

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
            Box {
                if (imageUris.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                }
                        ) {
                            AsyncImage(
                                model = imageUris[page],
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
                Row {
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
                }
            }
            Text(
                text = reviewDescription,
                maxLines = 3
            )
            FlowRow(
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
            }
        }
    }
}