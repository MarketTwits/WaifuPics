package com.markettwits.random_image.presentation.components.image.zoom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Composable
fun FullScreenImageDialog(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onClose: () -> Unit
) {
    val zoomState = rememberZoomState()
    Dialog(
        onDismissRequest = { onClose() },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = zoomState.scale,
                    scaleY = zoomState.scale
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .zoomable(zoomState),
                model = LocalImageLoader.current.memorySingle(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center
            )
        }
    }
}


