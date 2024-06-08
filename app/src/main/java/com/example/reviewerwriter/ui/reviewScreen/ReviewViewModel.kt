package com.example.reviewerwriter.ui.reviewScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewerwriter.data.dto.CriteriaEntity
import com.example.reviewerwriter.data.dto.TagEntity
import com.example.reviewerwriter.domain.reviewsUseCase.GetReviewInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel(
    getReviewInfoUseCase: GetReviewInfoUseCase
) : ViewModel() {
    var reviewTitle = mutableStateOf("")
    var reviewText = mutableStateOf("")
    var likesN = mutableIntStateOf(0)
    var photos = mutableStateOf(listOf(""))
    var tags = mutableStateOf(listOf(TagEntity(name = "", criteria = listOf(CriteriaEntity(name = "", value = 0)))))
    /*var reviewTitle = mutableStateOf("НАЗВАНИЕ РЕЦЕНЗИИ")
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
    )*/
    var criteria = mutableStateOf(listOf<CriteriaEntity>())
    var id = mutableIntStateOf(353)

    init {
        viewModelScope.launch {
            try {
                getReviewInfoUseCase.execute(id.intValue) { response ->
                    when(response.statusCode) {
                        200 -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main) {
                                    reviewTitle.value = response.value?.title.toString()
                                    reviewText.value = response.value?.mainText.toString()
                                    likesN.intValue = response.value?.likesN!!
                                    tags.value = response.value.tags
                                    photos.value = response.value.photos
                                    id.intValue = response.value.id
                                    Log.w("200", response.value.tags.toString())

                                    val arrayList = arrayListOf<CriteriaEntity>()
                                    tags.value.forEach { it ->
                                        it.criteria?.forEach {
                                            arrayList.add(
                                                CriteriaEntity(name = it.name, value = it.value)
                                            )
                                        }
                                    }
                                    criteria.value = arrayList.toList()
                                    Log.w("200", criteria.value.toString())
                                }
                            }
                        }
                        else -> {
                            Log.w("хихи", response.errors.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.w("хихи", "хаха2")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    var selectedImageUris = mutableStateOf<List<Uri>>(emptyList())
}