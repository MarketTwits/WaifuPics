package com.markettwits.waifupics.gallery.items.model

import kotlin.test.Test
import kotlin.test.assertEquals

class GallerySelectionRangeTest {
    private val photos = (1..12).toList()

    @Test fun resumingSelectionPreservesImagesChosenBeforeScrolling() {
        val previous = GallerySelectionRange(photos, emptySet(), 2).selectionAt(4)
        val resumed = GallerySelectionRange(photos, previous, 8)
        assertEquals(setOf(2, 3, 4, 8), resumed.selectionAt(8))
        assertEquals(setOf(2, 3, 4, 8, 9, 10), resumed.selectionAt(10))
        // Dragging backwards shrinks only this gesture's range.
        assertEquals(setOf(2, 3, 4, 8, 9), resumed.selectionAt(9))
        assertEquals(setOf(2, 3, 4), previous)
    }

    @Test fun resumingOnSelectedImageRemovesRangeWithoutLosingOtherSelections() {
        val previous = setOf(1, 2, 3, 4, 8, 10)
        val resumed = GallerySelectionRange(photos, previous, 3)
        assertEquals(setOf(1, 2, 4, 8, 10), resumed.selectionAt(3))
        assertEquals(setOf(1, 2, 8, 10), resumed.selectionAt(6))
        assertEquals(setOf(1, 2, 4, 8, 10), resumed.selectionAt(3))
    }

    @Test fun nextGestureUsesCommittedSelectionRatherThanAnEarlierBaseline() {
        val first = GallerySelectionRange(photos, setOf(1), 4).selectionAt(7)
        val second = GallerySelectionRange(photos, first, 6).selectionAt(5)
        val third = GallerySelectionRange(photos, second, 9).selectionAt(11)
        assertEquals(setOf(1, 4, 7), second)
        assertEquals(setOf(1, 4, 7, 9, 10, 11), third)
    }

    @Test fun resumingAfterGroupingOrGridChangesUsesCurrentVisualOrder() {
        val previous = setOf(2, 4)
        val reordered = listOf(12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
        val resumed = GallerySelectionRange(reordered, previous, 9)
        assertEquals(setOf(2, 4, 9, 8, 7, 6), resumed.selectionAt(6))
    }
}
