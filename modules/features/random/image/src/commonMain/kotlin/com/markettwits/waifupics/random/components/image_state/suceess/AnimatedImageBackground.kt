package com.markettwits.waifupics.random.components.image_state.suceess

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.MotionDurationScale
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

/** Blends the loaded image into a moving, low-resolution colour field behind the sharp image. */
@Composable
internal fun AnimatedImageBackground(
    painter: Painter,
    colorPalette: List<List<Int>>,
    animate: Boolean
) {
    val background = MaterialTheme.colorScheme.background
    val veil = background.copy(alpha = if (background.luminance() > 0.5f) 0.55f else 0.4f)
    val phase = remember { mutableFloatStateOf(0f) }
    val reveal = remember { Animatable(if (animate) 0f else 1f) }
    val focused = LocalWindowInfo.current.isWindowFocused
    val textureBlur = remember { supportsBackdropBlur() }
    val saturation = remember { ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(1.5f) }) }
    val colors = remember(colorPalette) {
        colorPalette.filter { it.size >= 3 }.map { rgb ->
            Color(rgb[0].coerceIn(0, 255), rgb[1].coerceIn(0, 255), rgb[2].coerceIn(0, 255))
        }.distinct().take(4)
    }

    LaunchedEffect(Unit) { if (animate) reveal.animateTo(1f, tween(600)) }
    LaunchedEffect(animate, focused) {
        if (!animate || !focused || (!textureBlur && colors.isEmpty())) return@LaunchedEffect
        val durationScale = currentCoroutineContext()[MotionDurationScale]
        snapshotFlow { durationScale?.scaleFactor ?: 1f }.collectLatest { scale ->
            if (scale <= 0f) return@collectLatest
            var previous = 0L
            while (currentCoroutineContext().isActive) {
                withFrameNanos { now ->
                    if (previous != 0L) {
                        val seconds = (now - previous).coerceIn(0L, 100_000_000L) / 1_000_000_000f
                        phase.floatValue = (phase.floatValue + seconds / scale) % 120f
                    }
                    previous = now
                }
            }
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(background), contentAlignment = Alignment.Center) {
        // Blur a quarter-size surface, then enlarge it: no full-screen blur buffer or extra image request.
        val layer = Modifier.size(maxWidth * 0.25f, maxHeight * 0.25f).graphicsLayer {
            scaleX = 4f
            scaleY = 4f
            alpha = reveal.value
        }
        Canvas(if (textureBlur) layer.blur(20.dp) else layer) {
            val seconds = phase.floatValue // Read motion only while drawing, without recomposing the feed.
            if (textureBlur) {
                drawCover(painter, size, Offset.Zero, saturation)
                withTransform({ rotate(-seconds * 15f, center) }) {
                    drawCover(painter, Size(size.width * 0.65f, size.height * 0.6f),
                        Offset(-size.width * 0.1f, -size.height * 0.05f), saturation, seconds * 30f, rotating = true)
                    drawCover(painter, Size(size.width * 0.65f, size.height * 0.6f),
                        Offset(size.width * 0.45f, size.height * 0.45f), saturation, seconds * 18f, rotating = true)
                }
            } else {
                drawPaletteField(colors, background, seconds)
            }
            drawRect(veil)
        }
    }
}

private fun DrawScope.drawCover(
    painter: Painter,
    area: Size,
    origin: Offset,
    filter: ColorFilter,
    angle: Float = 0f,
    rotating: Boolean = false
) {
    translate(origin.x, origin.y) {
        clipRect(0f, 0f, area.width, area.height) {
            withTransform({ rotate(angle, Offset(area.width / 2f, area.height / 2f)) }) {
                val intrinsic = painter.intrinsicSize
                val validSize = intrinsic.width.isFinite() && intrinsic.height.isFinite() &&
                    intrinsic.width > 0f && intrinsic.height > 0f
                // Overscan the rotating fragments so their rectangles remain filled at every angle.
                val diagonal = sqrt(area.width * area.width + area.height * area.height)
                val cover = if (rotating) Size(diagonal, diagonal) else area
                val factor = if (validSize) max(cover.width / intrinsic.width, cover.height / intrinsic.height) else 1f
                val target = if (validSize) intrinsic * factor else cover
                translate((area.width - target.width) / 2f, (area.height - target.height) / 2f) {
                    with(painter) { draw(target, colorFilter = filter) }
                }
            }
        }
    }
}

/** Android before API 31 has no Compose blur; these soft fields need no RenderEffect. */
private fun DrawScope.drawPaletteField(colors: List<Color>, background: Color, seconds: Float) {
    if (colors.isEmpty()) {
        drawRect(background)
        return
    }
    drawRect(colors.first())
    val periods = listOf(24f, 12f, 20f)
    repeat(3) { index ->
        val color = colors[(index + 1) % colors.size]
        val angle = seconds / periods[index] * (2f * kotlin.math.PI).toFloat() + index * 2f
        val center = Offset(size.width * (0.5f + cos(angle) * 0.35f),
            size.height * (0.5f + sin(angle) * 0.35f))
        drawRect(Brush.radialGradient(
            0f to color, 0.3f to color.copy(alpha = 0.9f), 0.6f to color.copy(alpha = 0.45f),
            0.85f to color.copy(alpha = 0.1f), 1f to color.copy(alpha = 0f),
            center = center, radius = max(size.width, size.height) * 0.8f
        ))
    }
}
