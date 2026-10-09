package com.markettwits.waifupics.gallery.items.components

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.foundation.OverscrollEffect
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
internal fun rememberGalleryOverscrollEffect(): GalleryOverscrollEffect {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    return remember(scope, density) {
        GalleryOverscrollEffect(scope, with(density) { 96.dp.toPx() }, with(density) { 2500.dp.toPx() })
    }
}

/** Move the grid beyond its edge with growing resistance, then restore it with a spring. */
internal class GalleryOverscrollEffect(
    private val scope: CoroutineScope,
    private val limit: Float,
    private val maxReleaseVelocity: Float,
) : OverscrollEffect {
    var displacement by mutableFloatStateOf(0f)
        private set
    private var rebound: Job? = null

    fun reset() {
        rebound?.cancel()
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
        if (userInput) {
            rebound?.cancel()
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
        if (userInput && abs(remaining) > 0.01f) {
            val resistance = 0.45f * (1f - abs(displacement) / limit).coerceIn(0f, 1f).let { it * it }
            displacement = (displacement + remaining * resistance).coerceIn(-limit, limit)
            pulled = remaining
        }
        return consumed.copy(y = consumed.y + released + pulled)
    }

    override suspend fun applyToFling(velocity: Velocity, performFling: suspend (Velocity) -> Velocity) {
        // A pull beyond an edge should spring back, rather than fling the grid behind it.
        val consumed = performFling(if (displacement != 0f) velocity.copy(y = 0f) else velocity)
        val remaining = velocity.y - consumed.y
        if (abs(displacement) > 0.01f || abs(remaining) > 1f) {
            rebound?.cancel()
            rebound = scope.launch {
                AnimationState(initialValue = displacement,
                    initialVelocity = (remaining * 0.3f).coerceIn(-maxReleaseVelocity, maxReleaseVelocity))
                    .animateTo(0f, spring(dampingRatio = 0.85f, stiffness = 350f)) {
                        displacement = value.coerceIn(-limit, limit)
                    }
                displacement = 0f
            }
            rebound?.join()
        }
    }
}
