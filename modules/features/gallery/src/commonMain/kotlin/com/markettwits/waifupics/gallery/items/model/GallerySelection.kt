package com.markettwits.waifupics.gallery.items.model

data class GalleryLayout(val columns: Int = 0, val byMonth: Boolean = false, val keepAspectRatio: Boolean = false)

/** Every update starts from the original selection; reversing restores items outside the range. */
internal class GallerySelectionRange(
    private val ids: List<Int>,
    private val original: Set<Int>,
    anchorId: Int,
    private val selecting: Boolean = anchorId !in original
) {
    private val anchor = ids.indexOf(anchorId)
    private val positions = ids.withIndex().associate { it.value to it.index }

    fun selectionAt(id: Int): Set<Int> {
        val end = positions[id] ?: return original
        if (anchor < 0) return original
        val range = ids.subList(minOf(anchor, end), maxOf(anchor, end) + 1).toSet()
        return if (selecting) original + range else original - range
    }
}

internal fun toggleGalleryGroup(original: Set<Int>, group: Set<Int>): Set<Int> =
    if (group.isNotEmpty() && original.containsAll(group)) original - group else original + group
