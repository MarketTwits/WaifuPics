package com.markettwits.waifupics.random.components.image_state.full_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity

/** Only the fitted artwork shares bounds; the backdrop and controls have their own transitions. */
@OptIn(ExperimentalSharedTransitionApi::class)
internal class HomeImageTransition(
    private val sharedScope: SharedTransitionScope,
    private val visibilityScope: AnimatedVisibilityScope,
    private val key: String
) {
    @Composable
    fun image(modifier: Modifier): Modifier = with(sharedScope) {
        modifier.sharedBounds(
            rememberSharedContentState(key), visibilityScope,
            boundsTransform = { _, _ -> tween(380, easing = FastOutSlowInEasing) },
            enter = fadeIn(tween(160)), exit = fadeOut(tween(160)),
            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(ContentScale.Fit)
        )
    }
}

@Composable
internal fun HomeSharedImage(
    painter: Painter,
    description: String?,
    imageTransition: HomeImageTransition,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    BoxWithConstraints(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val fitted = fitHomeImage(painter.intrinsicSize, Size(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat()))
        Image(painter, description, contentScale = ContentScale.Fit,
            modifier = imageTransition.image(Modifier.size(
                with(density) { fitted.width.toDp() }, with(density) { fitted.height.toDp() })))
    }
}

internal fun fitHomeImage(image: Size, viewport: Size): Size {
    if (!viewport.width.isFinite() || !viewport.height.isFinite() || viewport.width <= 0 || viewport.height <= 0)
        return Size.Zero
    if (!image.width.isFinite() || !image.height.isFinite() || image.width <= 0 || image.height <= 0)
        return viewport
    val scale = minOf(viewport.width / image.width, viewport.height / image.height)
    return image * scale
}
