package com.example.reviewerwriter.viewModel

import android.net.Uri
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReviewCreatingViewModel() : ViewModel() {
    var expandedTag =  mutableStateOf(false)
    var expandedCriteria =  mutableStateOf(false)
    var selectedCriteria =  mutableStateOf(listOf<String>())
    var selectedTags =  mutableStateOf(listOf<String>())
    val iconList = listOf(
        "-",
        "1",
        "2",
        "3",
        "4",
        "5"
    )

    var reviewTitle = mutableStateOf("")
    var reviewDescriptionField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var selectedText = mutableStateOf(iconList[0])

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    var selectedImageUris = mutableStateOf<List<Uri>>(emptyList())

    fun addSelectedCriteria(c: String){
        selectedCriteria.value = selectedCriteria.value + c
    }
    fun removeSelectedCriteria(c: String){
        selectedCriteria.value = selectedCriteria.value.filter { it != c }
    }
    fun addSelectedTag(tag: String, criteria: List<String>){
        selectedTags.value = selectedTags.value + tag
        criteria.forEach {
            if (it !in selectedCriteria.value) {
                selectedCriteria.value = selectedCriteria.value + it
            }
        }
    }
    fun removeSelectedTag(tag: String, criteria: List<String>){
        selectedTags.value = selectedTags.value.filter { it != tag }
        criteria.forEach {
            selectedCriteria.value = selectedCriteria.value - it

        }
    }
}