package com.markettwits.waifupics.random.components.image_state.suceess

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/** A short, local burst: no layout animation or pointer handlers over the scrolling feed. */
@Composable
internal fun FavoriteHeartOverlay(hearts: List<FavoriteHeart>, onFinished: (FavoriteHeart) -> Unit) {
    if (hearts.isEmpty()) return
    val finished = rememberUpdatedState(onFinished)
    val density = LocalDensity.current
    val colors = MaterialTheme.colorScheme
    val topColor = lerp(colors.primaryFixedDim, colors.onPrimaryFixedVariant, 0.16f)
    val bottomColor = lerp(colors.primaryFixedDim, colors.onPrimaryFixedVariant, 0.32f)
    val fill = remember(topColor, bottomColor) {
        Brush.linearGradient(listOf(topColor, bottomColor), Offset(4f, 3f), Offset(20f, 21f))
    }
    val heartPath = remember {
        Path().apply {
            moveTo(12f, 21.35f)
            lineTo(10.55f, 20.03f)
            cubicTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
            cubicTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
            cubicTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
            cubicTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
            cubicTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
            cubicTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 20.03f)
            close()
        }
    }
    BoxWithConstraints(Modifier.fillMaxSize().clearAndSetSemantics { }) {
        val diameter = with(density) { 176.dp.toPx() }
        val width = with(density) { maxWidth.toPx() }
        val height = with(density) { maxHeight.toPx() }
        hearts.forEach { heart ->
            key(heart.sequence) {
                val progress = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    progress.animateTo(1f, tween(1100, easing = LinearEasing))
                    finished.value(heart)
                }
                val t = progress.value
                val pop = when {
                    t < 0.18f -> mix(0.4f, 1.08f, easeOut(t / 0.18f))
                    t < 0.32f -> mix(1.08f, 1f, easeOut((t - 0.18f) / 0.14f))
                    t < 0.56f -> 1f
                    else -> mix(1f, 1.12f, easeOut((t - 0.56f) / 0.44f))
                }
                // Ease opacity at both ends so appearing, holding and fading join without a snap.
                val opacity = when {
                    t < 0.2f -> smoothFade(t / 0.2f)
                    t < 0.46f -> 1f
                    else -> 1f - smoothFade((t - 0.46f) / 0.54f)
                }
                // Keep the burst visible even when the finger lands close to an image edge.
                val half = diameter / 2
                val x = heart.position.x.coerceIn(half.coerceAtMost(width / 2), (width - half).coerceAtLeast(width / 2))
                val y = heart.position.y.coerceIn(half.coerceAtMost(height / 2), (height - half).coerceAtLeast(height / 2))
                Box(Modifier.offset { IntOffset((x - half).roundToInt(), (y - half).roundToInt()) }
                    .size(176.dp), contentAlignment = Alignment.Center) {
                    Canvas(Modifier.size(144.dp).graphicsLayer {
                        scaleX = pop
                        scaleY = pop
                        alpha = opacity
                        rotationZ = mix(-10f, -4f, easeOut(t))
                        translationY = -32.dp.toPx() * easeOut(((t - 0.25f) / 0.75f).coerceIn(0f, 1f))
                    }) {
                        // One smooth silhouette, without stacked icons, borders or a backdrop.
                        withTransform({ scale(size.width / 24f, size.height / 24f, pivot = Offset.Zero) }) {
                            drawPath(heartPath, fill)
                        }
                    }
                }
            }
        }
    }
}

private fun mix(from: Float, to: Float, fraction: Float) = from + (to - from) * fraction
private fun easeOut(fraction: Float): Float = 1f - (1f - fraction) * (1f - fraction) * (1f - fraction)
private fun smoothFade(fraction: Float): Float {
    val t = fraction.coerceIn(0f, 1f)
    return t * t * t * (t * (t * 6f - 15f) + 10f)
}
