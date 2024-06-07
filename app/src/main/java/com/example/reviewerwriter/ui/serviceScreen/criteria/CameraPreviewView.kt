package com.example.reviewerwriter.ui.serviceScreen.criteria

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraPreviewView(
    reviewCreatingViewModel: ReviewCreatingViewModel,
    context: Context,
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val showCameraPreview = reviewCreatingViewModel.showCameraPreview

    if (showCameraPreview.value) {
        Dialog(
            onDismissRequest = { reviewCreatingViewModel.showCameraPreview.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false) // Устанавливаем флаг для использования полного размера экрана
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                AndroidView(
                    factory = {
                        PreviewView(it).apply {
                            this.controller = controller
                            controller.bindToLifecycle(lifecycleOwner)
                        }
                    },
                    modifier = modifier
                )
                IconButton(
                    onClick = {
                        reviewCreatingViewModel.showCameraPreview.value = false
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .background(Color.Black.copy(alpha = 0.2f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "goBack",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            reviewCreatingViewModel.takePhoto(
                                controller = controller,
                                context = context
                            )
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(64.dp)
                            .background(Color.Black.copy(alpha = 0.2f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Take photo",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}