package com.markettwits.waifupics.gallery.items.components.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier

/** One image identity across the grid and viewer; thumbnails deliberately do not share it. */
@OptIn(ExperimentalSharedTransitionApi::class)
class GalleryImageTransition(
    private val sharedScope: SharedTransitionScope,
    private val visibilityScope: AnimatedVisibilityScope
) {
    val visible: Boolean get() = visibilityScope.transition.targetState == EnterExitState.Visible
    val running: Boolean get() = sharedScope.isTransitionActive || visibilityScope.transition.isRunning ||
        visibilityScope.transition.currentState != visibilityScope.transition.targetState

    @Composable
    fun image(modifier: Modifier, id: Int): Modifier = with(sharedScope) {
        modifier.sharedBounds(
            sharedContentState = rememberSharedContentState("gallery-photo-$id"),
            animatedVisibilityScope = visibilityScope,
            boundsTransform = { _, _ -> tween(360, easing = FastOutSlowInEasing) },
            enter = fadeIn(tween(180)), exit = fadeOut(tween(180)),
            clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium),
            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
        )
    }
}
