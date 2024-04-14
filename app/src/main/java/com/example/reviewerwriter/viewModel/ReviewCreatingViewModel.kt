package com.example.reviewerwriter.viewModel

import android.net.Uri
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReviewCreatingViewModel() : ViewModel() {
    val iconList = listOf(
        "-",
        "1",
        "2",
        "3",
        "4",
        "5"
    )

    //val navController = navController
    var reviewTitle = mutableStateOf("")
    var reviewDescriptionField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var selectedText = mutableStateOf(iconList[0])

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)

    var selectedImageUris = mutableStateOf<List<Uri>>(emptyList())

}