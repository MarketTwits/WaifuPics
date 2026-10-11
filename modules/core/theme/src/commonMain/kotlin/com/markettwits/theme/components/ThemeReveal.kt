package com.markettwits.theme.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.hypot
import kotlin.math.max

val LocalThemeReveal = staticCompositionLocalOf<ThemeRevealState?> { null }

/** Retains only the old frame; the new theme keeps drawing live throughout the reveal. */
@Stable
class ThemeRevealState internal constructor(private val scope: CoroutineScope) {
    var isAnimating by mutableStateOf(false)
        private set
    internal var oldFrame by mutableStateOf<ImageBitmap?>(null)
        private set
    internal val progress = Animatable(0f)
    internal var origin = Offset.Zero
        private set
    internal var targetDark = false
        private set
    internal var windowOrigin = Offset.Zero
    internal var captureFrame: (suspend () -> ImageBitmap)? = null
    private var dark by mutableStateOf(false)

    internal fun observeTheme(isDark: Boolean) { dark = isDark }

    fun changeTheme(buttonCenterInWindow: Offset?, persistChange: () -> Unit) {
        if (isAnimating) return
        val capture = captureFrame
        if (capture == null) {
            persistChange()
            return
        }
        // Guard synchronously, before launching: rapid taps must not enqueue toggles.
        val previousDark = dark
        targetDark = !previousDark
        isAnimating = true
        val localCenter = buttonCenterInWindow?.minus(windowOrigin)
        scope.launch {
            try {
                progress.snapTo(0f)
                val frame = try {
                    withTimeoutOrNull(1_000) { capture() }
                } catch (cancelled: CancellationException) {
                    throw cancelled
                } catch (_: Exception) {
                    null // Switching the theme must still work if capture is unavailable.
                }
                if (frame != null) {
                    origin = themeRevealOrigin(localCenter, Size(frame.width.toFloat(), frame.height.toFloat()))
                    oldFrame = frame
                }
                // Persist only after capture, so the snapshot cannot contain the new theme.
                persistChange()
                if (frame != null && withTimeoutOrNull(2_000) {
                        snapshotFlow { dark }.first { it != previousDark }
                    } != null) {
                    withFrameNanos { }
                    progress.animateTo(1f, tween(durationMillis = 400, easing = ThemeRevealEasing))
                }
            } finally {
                oldFrame = null
                isAnimating = false
            }
        }
    }
}

@Composable
internal fun rememberThemeRevealState(): ThemeRevealState {
    val scope = rememberCoroutineScope()
    return remember(scope) { ThemeRevealState(scope) }
}

@Composable
internal fun ThemeRevealHost(state: ThemeRevealState, content: @Composable () -> Unit) {
    val layer = rememberGraphicsLayer()
    val circle = remember { Path() }
    SideEffect { state.captureFrame = { layer.toImageBitmap() } }
    DisposableEffect(state) { onDispose { state.captureFrame = null } }
    Box(Modifier.fillMaxSize()
        .onGloballyPositioned { state.windowOrigin = it.positionInWindow() }
        .drawWithContent {
            val old = state.oldFrame
            if (!state.isAnimating) {
                // No bitmap allocation during normal rendering.
                layer.record { this@drawWithContent.drawContent() }
                drawLayer(layer)
            } else {
                drawContent()
                // A resize/rotation invalidates the screenshot geometry: show live content.
                if (old != null && old.width == size.width.toInt() && old.height == size.height.toInt()) {
                    val progress = state.progress.value
                    if (progress <= 0f) {
                        // Both directions start with a fully covered old frame, even while
                        // waiting for the settings flow. A zero-radius hole must not flash.
                        drawImage(old)
                    } else if (progress < 1f) {
                        val mask = themeRevealMask(state.targetDark, progress)
                        val radius = themeRevealRadius(state.origin, size) * mask.radiusFraction
                        circle.reset()
                        circle.addOval(Rect(center = state.origin, radius = radius))
                        clipPath(circle, clipOp = mask.clipOp) { drawImage(old) }
                    }
                }
            }
        }) { content() }
}

internal fun themeRevealOrigin(origin: Offset?, size: Size): Offset =
    if (origin == null || !origin.x.isFinite() || !origin.y.isFinite()) size.center
    else Offset(origin.x.coerceIn(0f, size.width), origin.y.coerceIn(0f, size.height))

internal fun themeRevealRadius(origin: Offset, size: Size): Float =
    hypot(max(origin.x, size.width - origin.x), max(origin.y, size.height - origin.y))

internal data class ThemeRevealMask(val radiusFraction: Float, val clipOp: ClipOp)

internal fun themeRevealMask(targetDark: Boolean, progress: Float): ThemeRevealMask {
    val fraction = progress.coerceIn(0f, 1f)
    // Dark pixels always form the circle: the new dark theme grows inside a hole
    // in the old light frame; the old dark frame contracts over the new light theme.
    return if (targetDark) ThemeRevealMask(fraction, ClipOp.Difference)
    else ThemeRevealMask(1f - fraction, ClipOp.Intersect)
}

internal val ThemeRevealEasing = Easing { fraction ->
    if (fraction < 0.5f) 2f * fraction * fraction
    else 1f - (-2f * fraction + 2f).let { it * it } / 2f
}
