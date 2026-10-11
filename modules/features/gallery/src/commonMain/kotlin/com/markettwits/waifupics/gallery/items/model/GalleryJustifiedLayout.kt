package com.markettwits.waifupics.gallery.items.model

import kotlin.math.ln
import kotlin.math.min
import kotlin.math.roundToInt

internal data class GalleryJustifiedCell(val image: ImageFavoriteUi.Base, val left: Int, val width: Int)
internal data class GalleryJustifiedRow(val cells: List<GalleryJustifiedCell>, val height: Int) {
    val key: String get() = "row-${cells.first().image.id}"
    fun photoAt(x: Float, y: Float): Int? = if (y < 0 || y >= height) null else
        cells.firstOrNull { x >= it.left && x < it.left + it.width }?.image?.id
}

/** Plan entire rows once, keeping the renderer, hit testing and scrollbar on the same pixels. */
internal fun galleryJustifiedRows(images: List<ImageFavoriteUi.Base>, availableWidth: Int,
    targetHeight: Int, spacing: Int, minimumTouchExtent: Int): List<GalleryJustifiedRow> {
    require(availableWidth > 0 && targetHeight > 0 && spacing >= 0 && minimumTouchExtent > 0)
    if (images.isEmpty()) return emptyList()
    val ratios = images.map { if (it.width > 0 && it.height > 0) it.width.toDouble() / it.height else 1.0 }
    val maximumItems = min(6, ((availableWidth + spacing) / (minimumTouchExtent + spacing)).coerceAtLeast(1))
    val costs = DoubleArray(images.size + 1) { Double.POSITIVE_INFINITY }.apply { this[images.size] = 0.0 }
    val counts = IntArray(images.size)
    // Bounded lookahead avoids orphan rows without an expensive search through all partitions.
    for (first in images.indices.reversed()) {
        var ratioSum = 0.0
        var smallestRatio = Double.POSITIVE_INFINITY
        for (count in 1..min(maximumItems, images.size - first)) {
            ratioSum += ratios[first + count - 1]
            smallestRatio = min(smallestRatio, ratios[first + count - 1])
            val width = availableWidth - spacing * (count - 1)
            val naturalHeight = width / ratioSum
            val last = first + count == images.size
            val cap = targetHeight * if (last) 1.25 else 2.0
            val height = min(naturalHeight, cap)
            if (count > 1 && height * smallestRatio < minimumTouchExtent) continue
            val difference = ln(naturalHeight / targetHeight)
            val cost = difference * difference * (if (last) 0.25 else 1.0) +
                (if (!last && naturalHeight > cap) 4.0 else 0.0) + 0.05 + costs[first + count]
            if (cost < costs[first]) {
                costs[first] = cost
                counts[first] = count
            }
        }
    }
    val rows = mutableListOf<GalleryJustifiedRow>()
    var first = 0
    while (first < images.size) {
        val count = counts[first].coerceAtLeast(1)
        val sum = (first until first + count).sumOf { ratios[it] }
        val budget = availableWidth - spacing * (count - 1)
        val naturalHeight = budget / sum
        val cap = targetHeight * if (first + count == images.size) 1.25 else 2.0
        val height = min(naturalHeight, cap).roundToInt().coerceAtLeast(1)
        val photoWidth = if (naturalHeight <= cap) budget else min(budget, (height * sum).roundToInt()).coerceAtLeast(count)
        // Allocate integer rounding cumulatively, so a full row ends exactly at the right inset.
        val widths = IntArray(count)
        var cumulative = 0.0
        var used = 0
        for (index in 0 until count) {
            cumulative += ratios[first + index]
            val end = (photoWidth * cumulative / sum).roundToInt().coerceIn(used + 1, photoWidth - (count - index - 1))
            widths[index] = end - used
            used = end
        }
        var left = 0
        val cells = (0 until count).map { index ->
            GalleryJustifiedCell(images[first + index], left, widths[index]).also { left += widths[index] + spacing }
        }
        rows.add(GalleryJustifiedRow(cells, height))
        first += count
    }
    return rows
}
