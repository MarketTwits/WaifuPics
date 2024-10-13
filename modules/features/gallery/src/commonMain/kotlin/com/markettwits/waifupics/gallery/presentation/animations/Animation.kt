package com.markettwits.waifupics.gallery.presentation.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

object Animation {
    /**
     * Value in ms
     */
    const val DEFAULT_LOW_VELOCITY_SWIPE_DURATION = 150

    /**
     * Smooth enough at 300ms
     */
    const val DEFAULT_NAVIGATION_ANIMATION_DURATION = 300

    /**
     * Syncs with status bar fade in/out
     */
    const val DEFAULT_TOP_BAR_ANIMATION_DURATION = 500

    val enterAnimation = fadeIn(tween(DEFAULT_LOW_VELOCITY_SWIPE_DURATION))
    val exitAnimation = fadeOut(tween(DEFAULT_LOW_VELOCITY_SWIPE_DURATION))

    val navigateInAnimation = fadeIn(tween(DEFAULT_NAVIGATION_ANIMATION_DURATION))
    val navigateUpAnimation = fadeOut(tween(DEFAULT_NAVIGATION_ANIMATION_DURATION))

    fun enterAnimation(durationMillis: Int): EnterTransition =
        fadeIn(tween(durationMillis))

    fun exitAnimation(durationMillis: Int): ExitTransition =
        fadeOut(tween(durationMillis))

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberPagerFlingBehavior(pagerState : PagerState) : TargetedFlingBehavior {
    return PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = tween(easing = LinearEasing,
            durationMillis = 150),
    )
}
