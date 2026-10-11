package com.markettwits.waifupics.gallery.items.components

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
internal fun rememberGalleryOverscrollEffect(state: LazyGridState): GalleryOverscrollEffect {
    val density = LocalDensity.current
    return remember(state, density) {
        GalleryOverscrollEffect(with(density) { 96.dp.toPx() }, with(density) { 2500.dp.toPx() },
            canScrollBackward = { state.canScrollBackward }, canScrollForward = { state.canScrollForward })
    }
}

/** Move the grid beyond its edge with growing resistance, then restore it with a spring. */
internal class GalleryOverscrollEffect(
    private val limit: Float,
    private val maxReleaseVelocity: Float,
    private val canScrollBackward: () -> Boolean,
    private val canScrollForward: () -> Boolean,
) : OverscrollEffect {
    var displacement by mutableFloatStateOf(0f)
        private set
    private var rebound: Job? = null
    private var generation = 0L

    // Pointer deltas/velocities are positive at the start and negative at the end.
    private fun atEdge(direction: Float): Boolean = when {
        direction > 0f -> !canScrollBackward()
        direction < 0f -> !canScrollForward()
        else -> false
    }

    fun reset() {
        generation++
        rebound?.cancel()
        rebound = null
        displacement = 0f
    }

    override val isInProgress: Boolean get() = abs(displacement) > 0.5f || rebound?.isActive == true

    override val node: Modifier.Node = object : Modifier.Node(), LayoutModifierNode {
        override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
            val child = measurable.measure(constraints)
            return layout(child.width, child.height) {
                // Actual placement also keeps shared-image and pointer coordinates aligned.
                child.place(0, displacement.roundToInt())
            }
        }

        override fun onDetach() {
            reset()
        }
    }

    override fun applyToScroll(delta: Offset, source: NestedScrollSource, performScroll: (Offset) -> Offset): Offset {
        val userInput = source == NestedScrollSource.UserInput
        var released = 0f
        if (displacement != 0f && !atEdge(displacement)) reset()
        if (userInput) {
            generation++
            rebound?.cancel()
            rebound = null
            // Returning towards the edge releases tension before scrolling the real content.
            if (displacement * delta.y < 0f) {
                val toEdge = -displacement / 0.45f
                released = if (delta.y > 0f) minOf(delta.y, toEdge) else maxOf(delta.y, toEdge)
                displacement += released * 0.45f
                if (abs(displacement) < 0.01f) displacement = 0f
            }
        }
        val consumed = performScroll(delta.copy(y = delta.y - released))
        val remaining = delta.y - released - consumed.y
        var pulled = 0f
        if (userInput && abs(remaining) > 0.01f && atEdge(remaining)) {
            val resistance = 0.45f * (1f - abs(displacement) / limit).coerceIn(0f, 1f).let { it * it }
            displacement = (displacement + remaining * resistance).coerceIn(-limit, limit)
            pulled = remaining
        }
        // A nested/programmatic scroll can move away from the edge during performScroll.
        if (displacement != 0f && !atEdge(displacement)) reset()
        return consumed.copy(y = consumed.y + released + pulled)
    }

    override suspend fun applyToFling(velocity: Velocity, performFling: suspend (Velocity) -> Velocity) {
        if (displacement != 0f && !atEdge(displacement)) reset()
        val flingGeneration = generation
        // Release an actual edge pull; never suppress an ordinary fling in the middle.
        val consumed = performFling(if (displacement != 0f) velocity.copy(y = 0f) else velocity)
        if (flingGeneration != generation) return
        if (displacement != 0f && !atEdge(displacement)) reset()
        val remaining = velocity.y - consumed.y
        val edgeVelocity = if (atEdge(remaining)) remaining else 0f
        if (abs(displacement) <= 0.01f && abs(edgeVelocity) <= 1f) return
        val direction = if (displacement != 0f) displacement else edgeVelocity
        val releaseVelocity = (edgeVelocity * 0.3f).coerceIn(-maxReleaseVelocity, maxReleaseVelocity)
        rebound?.cancel()
        // The spring belongs to this fling: cancelling the fling cancels the spring too.
        coroutineScope {
            val animation = launch {
                AnimationState(initialValue = displacement, initialVelocity = releaseVelocity)
                    .animateTo(0f, spring(dampingRatio = 0.85f, stiffness = 350f)) {
                        if (flingGeneration != generation || !atEdge(direction)) {
                            cancelAnimation()
                        } else {
                            displacement = if (direction > 0f) value.coerceIn(0f, limit)
                                else value.coerceIn(-limit, 0f)
                        }
                    }
            }
            rebound = animation
            try {
                animation.join()
            } finally {
                if (rebound === animation) {
                    rebound = null
                    displacement = 0f
                }
            }
        }
    }
}
