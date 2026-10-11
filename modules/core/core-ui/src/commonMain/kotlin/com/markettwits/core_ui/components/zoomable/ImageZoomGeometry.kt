package com.markettwits.core_ui.components.zoomable

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

internal enum class ImageZoomStage { Fit, Cover, Original, Gesture }

/** Sizes are physical pixels: one image pixel per viewport pixel at originalScale. */
internal data class ImageZoomGeometry(val coverScale: Float, val originalScale: Float, val maxScale: Float) {
    fun scale(stage: ImageZoomStage): Float = when (stage) {
        ImageZoomStage.Fit, ImageZoomStage.Gesture -> 1f
        ImageZoomStage.Cover -> coverScale
        ImageZoomStage.Original -> originalScale
    }.coerceIn(1f, maxScale)

    fun nextStage(stage: ImageZoomStage, currentScale: Float): ImageZoomStage? {
        if (stage == ImageZoomStage.Gesture) return ImageZoomStage.Fit
        var candidate = stage
        repeat(3) {
            candidate = when (candidate) {
                ImageZoomStage.Fit -> ImageZoomStage.Cover
                ImageZoomStage.Cover -> ImageZoomStage.Original
                ImageZoomStage.Original, ImageZoomStage.Gesture -> ImageZoomStage.Fit
            }
            if (abs(scale(candidate) - currentScale) > 0.001f) return candidate
        }
        return null
    }

    companion object {
        fun calculate(viewport: Size, imagePixels: Size, maxScale: Float): ImageZoomGeometry? {
            if (!viewport.validPixels() || !imagePixels.validPixels() || !maxScale.isFinite() || maxScale < 1f) return null
            val fit = min(viewport.width / imagePixels.width, viewport.height / imagePixels.height)
            val cover = max(viewport.width / imagePixels.width, viewport.height / imagePixels.height) / fit
            return ImageZoomGeometry(cover.coerceIn(1f, maxScale), (1f / fit).coerceIn(1f, maxScale), maxScale)
        }
    }
}

internal fun Size.validPixels(): Boolean = width.isFinite() && height.isFinite() && width > 0f && height > 0f

/** Keep the tapped image point at the same viewport position before clamping at content edges. */
internal fun imageZoomOffset(viewport: Size, offset: Offset, currentScale: Float,
    targetScale: Float, focalPoint: Offset, pan: Offset = Offset.Zero): Offset {
    val ratio = targetScale / currentScale
    val focalFromCenter = focalPoint - Offset(viewport.width / 2f, viewport.height / 2f)
    return offset * ratio + focalFromCenter * (1f - ratio) + pan
}
