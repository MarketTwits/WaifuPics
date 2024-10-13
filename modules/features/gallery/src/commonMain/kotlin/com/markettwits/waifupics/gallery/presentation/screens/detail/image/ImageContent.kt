package com.markettwits.waifupics.gallery.presentation.screens.detail.image

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.markettwits.core_ui.components.zoomable.rememberZoomState
import com.markettwits.core_ui.components.zoomable.zoomable
import com.markettwits.waifupics.gallery.presentation.animations.Animation.DEFAULT_TOP_BAR_ANIMATION_DURATION
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.Res
import waifupics.modules.features.gallery.generated.resources.image


@Composable
fun ZoomablePagerImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    uiEnabled: Boolean,
    maxScale: Float = 25f,
    onItemClick: () -> Unit,
    setCurrentItem: () -> Unit
) {
    val zoomState = rememberZoomState(
        maxScale = maxScale
    )
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        contentScale = ContentScale.Fit,
        onSuccess = {
            zoomState.setContentSize(it.painter.intrinsicSize)
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        val blurAlpha by animateFloatAsState(
            animationSpec = tween(DEFAULT_TOP_BAR_ANIMATION_DURATION),
            targetValue = if (uiEnabled) 0.7f else 0f,
            label = "blurAlpha"
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(blurAlpha)
                .blur(100.dp),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = modifier
                .fillMaxSize()
                .zoomable(
                    onTap = {
                        onItemClick()
                    },
                    zoomState = zoomState,
                ),
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(Res.string.image)
        )
    }
    setCurrentItem()
}