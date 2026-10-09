package com.markettwits.waifupics.gallery.item.components.filmstrip

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/** Let the release coast before centering, with a bounded distance even on a fast fling. */
@Composable
internal fun rememberFilmstripFlingBehavior(): FlingBehavior {
    val density = LocalDensity.current
    return remember(density) {
        val maxVelocity = with(density) { 800.dp.toPx() }
        val decay = exponentialDecay<Float>(frictionMultiplier = 0.85f,
            absVelocityThreshold = with(density) { 8.dp.toPx() })
        object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                var previous = 0f
                var remainingVelocity = 0f
                AnimationState(initialValue = 0f,
                    initialVelocity = initialVelocity.coerceIn(-maxVelocity, maxVelocity)).animateDecay(decay) {
                    val delta = value - previous
                    val consumed = scrollBy(delta)
                    previous = value
                    if (abs(delta - consumed) > 0.5f) {
                        remainingVelocity = velocity
                        cancelAnimation()
                    }
                }
                return remainingVelocity
            }
        }
    }
}
