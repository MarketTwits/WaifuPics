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

internal data class GalleryScrollRow(val firstItem: Int, val offset: Int, val section: GallerySection,
    val height: Int, val photoIds: List<Int> = emptyList(), val justified: GalleryJustifiedRow? = null)
internal data class GalleryScrollTarget(val item: Int, val offset: Int)

/** Uses the same header, cell and spacing geometry as the grid, including incomplete day rows. */
internal class GalleryScrollGeometry(
    sections: List<GallerySection>,
    columns: Int,
    headerHeight: Int,
    photoHeight: Int,
    spacing: Int,
    private val topPadding: Int,
    bottomPadding: Int,
    viewportHeight: Int,
    justifiedRows: Map<String, List<GalleryJustifiedRow>>? = null
) {
    val rows: List<GalleryScrollRow>
    private val itemToRow: IntArray
    private val photoItems = mutableMapOf<Int, Int>()
    val maxOffset: Int
    init {
        require(columns > 0 && headerHeight > 0 && photoHeight > 0)
        val list = mutableListOf<GalleryScrollRow>()
        val mapping = mutableListOf<Int>()
        var offset = 0
        var item = 0
        sections.forEach { section ->
            list.add(GalleryScrollRow(item++, offset, section, headerHeight))
            mapping.add(list.lastIndex)
            offset += headerHeight + spacing
            if (justifiedRows != null) {
                justifiedRows.getValue(section.key).forEach { row ->
                    list.add(GalleryScrollRow(item, offset, section, row.height, row.cells.map { it.image.id }, row))
                    mapping.add(list.lastIndex)
                    row.cells.forEach { photoItems[it.image.id] = item }
                    item++
                    offset += row.height + spacing
                }
            } else {
                section.images.chunked(columns).forEach { row ->
                    list.add(GalleryScrollRow(item, offset, section, photoHeight, row.map { it.id }))
                    repeat(row.size) { index ->
                        mapping.add(list.lastIndex)
                        photoItems[row[index].id] = item + index
                    }
                    item += row.size
                    offset += photoHeight + spacing
                }
            }
        }
        rows = list
        itemToRow = mapping.toIntArray()
        maxOffset = (topPadding + offset - (if (list.isEmpty()) 0 else spacing) + bottomPadding - viewportHeight)
            .coerceAtLeast(0)
    }

    fun itemForPhoto(id: Int): Int? = photoItems[id]

    fun firstPhotoInItem(item: Int): Int? = rowForItem(item)?.photoIds?.firstOrNull()

    fun photoAt(item: Int, x: Float, y: Float): Int? = rowForItem(item)?.justified?.photoAt(x, y)

    private fun rowForItem(item: Int): GalleryScrollRow? =
        if (rows.isEmpty()) null else rows[itemToRow[item.coerceIn(itemToRow.indices)]]

    fun sectionOfItem(item: Int): GallerySection? =
        if (rows.isEmpty()) null else rows[itemToRow[item.coerceIn(itemToRow.indices)]].section

    fun offsetOf(item: Int, itemOffset: Int): Int =
        if (rows.isEmpty()) 0 else (rows[itemToRow[item.coerceIn(itemToRow.indices)]].offset + itemOffset)
            .coerceIn(0, maxOffset)

    /** An overlay switches dates only when the next section's real header reaches the top. */
    fun pinnedSection(item: Int, itemOffset: Int): GallerySection? {
        val offset = offsetOf(item, itemOffset) - topPadding
        return if (rows.isEmpty() || offset < 0) null else rowAt(offset).section
    }

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
