package com.markettwits.waifupics.gallery.items.model

import kotlinx.datetime.LocalDate
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.test.*

class GalleryJustifiedLayoutTest {
    private fun photo(id: Int, width: Int = 100, height: Int = 100) =
        ImageFavoriteUi.Base(id, "image-$id", width, height, "09.10.2026", false)

    private fun plan(images: List<ImageFavoriteUi.Base>, width: Int = 320, target: Int = 100) =
        galleryJustifiedRows(images, width, target, 8, 48)

    @Test fun fullRowsShareHeightAndEndAtTheSameRightInset() {
        val rows = plan((1..6).map { photo(it) })
        assertEquals(listOf(3, 3), rows.map { it.cells.size })
        rows.forEach { row ->
            assertEquals(0, row.cells.first().left)
            assertEquals(320, row.cells.last().let { it.left + it.width })
            row.cells.zipWithNext().forEach { (a, b) -> assertEquals(a.left + a.width + 8, b.left) }
        }
    }

    @Test fun mixedArtPreservesOrderAndActualRatiosWithoutCropping() {
        val images = listOf(photo(1, 640, 480), photo(2, 480, 800), photo(3, 1280, 720),
            photo(4, 400, 900), photo(5, 500, 500), photo(6, 900, 600))
        val rows = plan(images)
        assertEquals(images.map { it.id }, rows.flatMap { it.cells }.map { it.image.id })
        rows.forEach { row -> row.cells.forEach { cell ->
            val expectedWidth = row.height * cell.image.width.toDouble() / cell.image.height
            assertTrue(abs(expectedWidth - cell.width) <= 2.5, "Frame must match art ratio: $cell")
            assertTrue(cell.left + cell.width <= 320)
        } }
    }

    @Test fun shortLastRowStaysSmallAndLeftAligned() {
        val row = plan(listOf(photo(1))).single()
        assertEquals(125, row.height)
        assertEquals(125, row.cells.single().width)
        assertEquals(0, row.cells.single().left)
    }

    @Test fun missingDimensionsUseSquareFrames() {
        val row = plan(listOf(photo(1, 0, 500), photo(2, 400, -1), photo(3, 0, 0))).single()
        assertEquals(3, row.cells.size)
        row.cells.forEach { assertTrue(abs(it.width - row.height) <= 1) }
    }

    @Test fun extremeRatiosAndNarrowViewportsHaveValidBounds() {
        val images = listOf(photo(1, 1, Int.MAX_VALUE), photo(2, Int.MAX_VALUE, 1), photo(3))
        for (width in listOf(1, 35, 101, 1080)) {
            val rows = plan(images, width)
            assertEquals(images.map { it.id }, rows.flatMap { it.cells }.map { it.image.id })
            rows.forEach { row ->
                assertTrue(row.height > 0)
                row.cells.forEach { assertTrue(it.width > 0 && it.left >= 0 && it.left + it.width <= width) }
            }
        }
        assertTrue(plan(emptyList()).isEmpty())
    }

    @Test fun touchLookupRejectsGapsAndSparseRowRemainder() {
        val row = plan((1..3).map { photo(it) }).single()
        row.cells.forEach { cell ->
            assertEquals(cell.image.id, row.photoAt(cell.left + cell.width / 2f, row.height / 2f))
        }
        val first = row.cells.first()
        assertNull(row.photoAt(first.left + first.width + 4f, 10f))
        assertNull(row.photoAt(10f, -1f))
        assertNull(row.photoAt(10f, row.height.toFloat()))
        assertNull(plan(listOf(photo(1))).single().photoAt(200f, 10f))
    }

    private fun variableGeometry(): GalleryScrollGeometry {
        val a = GallerySection(LocalDate(2026, 10, 9), listOf(photo(1), photo(2), photo(3)))
        val b = GallerySection(LocalDate(2026, 10, 8), listOf(photo(4)))
        val planned = mapOf(a.key to listOf(
            GalleryJustifiedRow(listOf(GalleryJustifiedCell(a.images[0], 0, 120),
                GalleryJustifiedCell(a.images[1], 128, 192)), 96),
            GalleryJustifiedRow(listOf(GalleryJustifiedCell(a.images[2], 0, 240)), 240)),
            b.key to listOf(GalleryJustifiedRow(listOf(GalleryJustifiedCell(b.images[0], 0, 100)), 100)))
        return GalleryScrollGeometry(listOf(a, b), 3, 48, 100, 8, 8, 16, 200, planned)
    }

    @Test fun variableRowsDriveExactScrollOffsetsAndPhotoAnchors() {
        val geometry = variableGeometry()
        assertEquals(listOf(0, 1, 2, 3, 4), geometry.rows.map { it.firstItem })
        assertEquals(listOf(0, 56, 160, 408, 464), geometry.rows.map { it.offset })
        assertEquals(388, geometry.maxOffset)
        assertEquals(1, geometry.itemForPhoto(1))
        assertEquals(1, geometry.itemForPhoto(2))
        assertEquals(2, geometry.itemForPhoto(3))
        assertEquals(4, geometry.itemForPhoto(4))
        assertNull(geometry.itemForPhoto(99))
        for (step in 0..100) {
            val fraction = step / 100f
            val target = geometry.target(fraction)
            assertEquals((fraction * geometry.maxOffset).toInt(), geometry.offsetOf(target.item, target.offset))
        }
    }

    @Test fun variableRowHitTestingSelectsTheActualPhoto() {
        val geometry = variableGeometry()
        assertEquals(1, geometry.photoAt(1, 60f, 40f))
        assertEquals(2, geometry.photoAt(1, 200f, 40f))
        assertNull(geometry.photoAt(1, 124f, 40f))
        assertNull(geometry.photoAt(0, 60f, 40f))
        assertEquals(1, geometry.firstPhotoInItem(1))
        assertEquals(3, geometry.firstPhotoInItem(2))
        assertNull(geometry.firstPhotoInItem(0))
    }

    @Test fun longMixedGalleryHasDeterministicBoundedRows() {
        val images = (1..2000).map { photo(it, 100 + (it * 17) % 700, 100 + (it * 31) % 1000) }
        val rows = plan(images, 1080, 250)
        assertEquals(rows, plan(images, 1080, 250))
        assertEquals(images.size, rows.sumOf { it.cells.size })
        assertTrue(rows.all { it.cells.size in 1..6 })
        assertEquals(rows.size, rows.map { it.key }.distinct().size)
        rows.forEach { row -> row.cells.forEach { cell ->
            assertTrue(abs((row.height * cell.image.width.toDouble() / cell.image.height).roundToInt() - cell.width) <= 4)
        } }
    }
}
