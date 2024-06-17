package com.markettwits.random_image.presentation.components.image.full_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent

import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Composable
internal fun FullImageScreen(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val zoomState = rememberZoomState(maxScale = 25f)
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                filterQuality = FilterQuality.Medium,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(zoomState = zoomState),
                error = {
                    if (imageUrl.isBlank())
                        Box(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer))
                    else
                        Box(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer))
                },
                loading = {
                    Box(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer))
                },
                success = {
                    zoomState.setContentSize(it.painter.intrinsicSize)
                    SubcomposeAsyncImageContent(modifier = Modifier)
                }
            )
            SmallFloatingActionButton(
                modifier = Modifier.padding(10.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary,
                onClick = {
                   onDismiss()
                },
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Floating action button."
                )
            }
        }
    }
}