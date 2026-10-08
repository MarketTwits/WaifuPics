package com.markettwits.waifupics.random.components.image_state.suceess

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.dp

/** Blends the Fit boundaries into their background fields without changing image geometry. */
internal fun Modifier.softFitEdges(imageSize: Size): Modifier = drawWithCache {
    val validSize = imageSize.width.isFinite() && imageSize.height.isFinite() &&
        imageSize.width > 0f && imageSize.height > 0f && size.width > 0f && size.height > 0f
    if (!validSize) {
        onDrawWithContent { drawContent() }
    } else {
        val scale = minOf(size.width / imageSize.width, size.height / imageSize.height)
        val fittedSize = Size(imageSize.width * scale, imageSize.height * scale)
        val left = (size.width - fittedSize.width) / 2f
        val top = (size.height - fittedSize.height) / 2f
        val imageBounds = Rect(Offset(left, top), fittedSize)
        val minimumField = 8.dp.toPx()
        val verticalFields = top >= minimumField
        val horizontalFields = left >= minimumField
        val mask = when {
            verticalFields -> {
                val feather = minOf(4.dp.toPx(), fittedSize.height * 0.01f) / fittedSize.height
                Brush.verticalGradient(
                    0f to Color.Transparent, feather to Color.White,
                    1f - feather to Color.White, 1f to Color.Transparent,
                    startY = imageBounds.top, endY = imageBounds.bottom
                )
            }
            horizontalFields -> {
                val feather = minOf(4.dp.toPx(), fittedSize.width * 0.01f) / fittedSize.width
                Brush.horizontalGradient(
                    0f to Color.Transparent, feather to Color.White,
                    1f - feather to Color.White, 1f to Color.Transparent,
                    startX = imageBounds.left, endX = imageBounds.right
                )
            }
            else -> null
        }
        val layerPaint = Paint()
        onDrawWithContent {
            if (mask == null) drawContent()
            else {
                val canvas = drawContext.canvas
                canvas.saveLayer(imageBounds, layerPaint)
                try {
                    drawContent()
                    drawRect(mask, topLeft = imageBounds.topLeft, size = fittedSize, blendMode = BlendMode.DstIn)
                } finally {
                    canvas.restore()
                }
            }
        }
    }
}
