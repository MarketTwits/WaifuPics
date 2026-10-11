package com.markettwits.waifupics.gallery.items.model

internal data class GalleryScrollbarThumb(val top: Float, val height: Float, val travel: Float) {
    val center: Float get() = top + height / 2f

    fun progressAfterDrag(startProgress: Float, delta: Float): Float =
        (startProgress + delta / travel.coerceAtLeast(1f)).coerceIn(0f, 1f)
}

/** Compact fast-scroll handle; only overscroll changes its height, never the gallery length. */
internal fun galleryScrollbarThumb(
    track: Float, viewport: Float, maxOffset: Float, progress: Float, overscroll: Float,
    restLength: Float, minOverscrollLength: Float,
): GalleryScrollbarThumb {
    if (track <= 0f || viewport <= 0f || maxOffset <= 0f) return GalleryScrollbarThumb(0f, 0f, 0f)
    val position = progress.coerceIn(0f, 1f)
    val pulled = if (position == 0f || position == 1f) kotlin.math.abs(overscroll) else 0f
    val restingHeight = restLength.coerceIn(0f, track)
    val compressedMinimum = minOverscrollLength.coerceIn(0f, restingHeight)
    val compressedHeight = restingHeight * (1f - (pulled / viewport / 0.2f).coerceIn(0f, 1f))
    val height = compressedHeight.coerceIn(compressedMinimum, restingHeight)
    val travel = (track - height).coerceAtLeast(0f)
    return GalleryScrollbarThumb(position * travel, height, travel)
}
