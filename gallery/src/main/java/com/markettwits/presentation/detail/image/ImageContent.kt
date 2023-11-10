package com.markettwits.presentation.detail.image

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Composable
fun ImageContent(imageUrl : String, paddingValues: PaddingValues){
    val zoomState = rememberZoomState()
    AsyncImage(
        modifier = Modifier
            .graphicsLayer(
                scaleX = zoomState.scale,
                scaleY = zoomState.scale
            )
            .padding(paddingValues)
            .fillMaxWidth()
            .zoomable(zoomState),
        model = LocalImageLoader.current.single(imageUrl),
        contentScale = ContentScale.Fit,
        contentDescription = ""
    )
}