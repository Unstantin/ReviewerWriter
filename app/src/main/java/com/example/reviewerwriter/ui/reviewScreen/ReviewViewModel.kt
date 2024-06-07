package com.example.reviewerwriter.ui.reviewScreen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.domain.etites.Criteria
import com.example.reviewerwriter.domain.etites.Tag

class ReviewViewModel : ViewModel() {
    var reviewTitle = mutableStateOf("НАЗВАНИЕ РЕЦЕНЗИИ")
    var reviewText = mutableStateOf("ОСНОВНОЙ ТЕКСТ ОСНОВНОЙ ТЕКСТ " +
            "ОСНОВНОЙ ТЕКСТ ОСНОВНОЙ ТЕКСТ ОСНОВНОЙ " +
            "ТЕКСТ ОСНОВНОЙ ТЕКСТОСНОВНОЙ ТЕКСТ ОСНОВНОЙ ТЕКСТ" +
            "ОСНОВНОЙ ТЕКСТ ОСНОВНОЙ ТЕКСТ")
    var likesN = mutableIntStateOf(120)
    var tags = mutableStateOf(
        listOf(
            Tag(
                name = "tag1",
                criteria = listOf(
                    Criteria(
                        name = "Загруженность",
                        value = 5
                    ),
                    Criteria(
                        name = "Персонал",
                        value = 2
                    )
                )
            ),
            Tag(
                name = "tag2",
                criteria = listOf(
                    Criteria(
                        name = "Комфорт",
                        value = 3
                    ),
                    Criteria(
                        name = "Разнообразие",
                        value = 4
                    )
                )
            ),
            Tag(
                name = "tag3",
                criteria = listOf(
                    Criteria(
                        name = "Сервис",
                        value = 1
                    )
                )
            ),
            Tag(
                name = "tag4",
                criteria = listOf(
                    Criteria(
                        name = "Местоположение",
                        value = 3
                    )
                )
            )
        )
    )
    @SuppressLint("MutableCollectionMutableState")
    var criteria = mutableStateOf(mutableMapOf<String, Int>())

    init {
        tags.value.forEach { it ->
            it.criteria.forEach {
                criteria.value[it.name] = it.value!!
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    var selectedImageUris = mutableStateOf<List<Uri>>(emptyList())
}