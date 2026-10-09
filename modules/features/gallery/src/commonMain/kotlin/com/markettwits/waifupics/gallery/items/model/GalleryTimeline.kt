package com.markettwits.waifupics.gallery.items.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal data class GallerySection(val date: LocalDate?, val images: List<ImageFavoriteUi.Base>) {
    val key: String get() = date?.toString() ?: "undated"
}

internal fun gallerySections(images: List<ImageFavoriteUi>, zone: TimeZone, byMonth: Boolean = false): List<GallerySection> =
    images.filterIsInstance<ImageFavoriteUi.Base>().groupBy { image ->
        image.savedDate(zone)?.let { if (byMonth) LocalDate(it.year, it.month.ordinal + 1, 1) else it }
    }
        .entries.sortedWith(compareByDescending { it.key })
        .map { (date, items) -> GallerySection(date, items) }

@OptIn(ExperimentalTime::class)
private fun ImageFavoriteUi.savedDate(zone: TimeZone): LocalDate? {
    savedAt?.let { return Instant.fromEpochMilliseconds(it).toLocalDateTime(zone).date }
    val parts = created.split('.')
    if (parts.size != 3) return null
    return runCatching { LocalDate(parts[2].toInt(), parts[1].toInt(), parts[0].toInt()) }.getOrNull()
}

internal data class GalleryScrollRow(val firstItem: Int, val offset: Int, val section: GallerySection)
internal data class GalleryScrollTarget(val item: Int, val offset: Int)

/** Uses the same header, cell and spacing geometry as the grid, including incomplete day rows. */
internal class GalleryScrollGeometry(
    sections: List<GallerySection>,
    columns: Int,
    headerHeight: Int,
    photoHeight: Int,
    spacing: Int,
    topPadding: Int,
    bottomPadding: Int,
    viewportHeight: Int
) {
    val rows: List<GalleryScrollRow>
    private val itemToRow: IntArray
    val maxOffset: Int
    init {
        require(columns > 0 && headerHeight > 0 && photoHeight > 0)
        val list = mutableListOf<GalleryScrollRow>()
        val mapping = mutableListOf<Int>()
        var offset = 0
        var item = 0
        sections.forEach { section ->
            list.add(GalleryScrollRow(item++, offset, section))
            mapping.add(list.lastIndex)
            offset += headerHeight + spacing
            section.images.chunked(columns).forEach { row ->
                list.add(GalleryScrollRow(item, offset, section))
                repeat(row.size) { mapping.add(list.lastIndex) }
                item += row.size
                offset += photoHeight + spacing
            }
        }
        rows = list
        itemToRow = mapping.toIntArray()
        maxOffset = (topPadding + offset - (if (list.isEmpty()) 0 else spacing) + bottomPadding - viewportHeight)
            .coerceAtLeast(0)
    }

    fun sectionOfItem(item: Int): GallerySection? =
        if (rows.isEmpty()) null else rows[itemToRow[item.coerceIn(itemToRow.indices)]].section

    fun offsetOf(item: Int, itemOffset: Int): Int =
        if (rows.isEmpty()) 0 else (rows[itemToRow[item.coerceIn(itemToRow.indices)]].offset + itemOffset)
            .coerceIn(0, maxOffset)

    fun target(fraction: Float): GalleryScrollTarget {
        if (rows.isEmpty()) return GalleryScrollTarget(0, 0)
        val offset = (fraction.coerceIn(0f, 1f) * maxOffset).toInt()
        val row = rowAt(offset)
        return GalleryScrollTarget(row.firstItem, offset - row.offset)
    }

    fun sectionAt(fraction: Float): GallerySection? =
        rows.takeIf { it.isNotEmpty() }?.let {
            if (fraction >= 1f) rows.last().section
            else rowAt((fraction.coerceIn(0f, 1f) * maxOffset).toInt()).section
        }

    private fun rowAt(offset: Int): GalleryScrollRow {
        var low = 0
        var high = rows.lastIndex
        while (low < high) {
            val mid = (low + high + 1) / 2
            if (rows[mid].offset <= offset) low = mid else high = mid - 1
        }
        return rows[low]
    }
}
