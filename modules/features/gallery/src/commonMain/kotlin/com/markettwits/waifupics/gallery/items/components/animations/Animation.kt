package com.markettwits.waifupics.gallery.items.components.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

object Animation {
    /**
     * Value in ms
     */
    const val DEFAULT_LOW_VELOCITY_SWIPE_DURATION = 150

    const val DEFAULT_TOP_BAR_ANIMATION_DURATION = 500

    val enterAnimation = fadeIn(tween(DEFAULT_LOW_VELOCITY_SWIPE_DURATION))
    val exitAnimation = fadeOut(tween(DEFAULT_LOW_VELOCITY_SWIPE_DURATION))

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
