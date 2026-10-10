package com.markettwits.waifupics.gallery.items.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlin.test.*
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class GalleryTimelineTest {
    private fun photo(id: Int, timestamp: String? = null, date: String = "") = ImageFavoriteUi.Base(
        id, "image-$id", 100, 100, date, false,
        timestamp?.let { Instant.parse(it).toEpochMilliseconds() })

    @Test fun groupsByLocalSavedDayRatherThanFormattedTextOrUtcDay() {
        val sections = gallerySections(listOf(
            photo(1, "2026-10-08T16:59:00Z", "wrong"),
            photo(2, "2026-10-08T17:01:00Z", "wrong"),
            photo(3, "2026-10-09T06:00:00Z", "wrong")
        ), TimeZone.of("UTC+07:00"))
        assertEquals(listOf(LocalDate(2026, 10, 9), LocalDate(2026, 10, 8)), sections.map { it.date })
        assertEquals(listOf(2, 3), sections.first().images.map { it.id })
        assertEquals(listOf(1), sections.last().images.map { it.id })
    }

    @Test fun legacyDatesAreChronologicalAndInvalidDatesRemainAccessibleAtTheEnd() {
        val sections = gallerySections(listOf(
            photo(1, date = "31.12.2025"), photo(2, date = "01.01.2026"),
            photo(3, date = "29.02.2024"), photo(4, date = "29.02.2025"),
            photo(5, date = "")
        ), TimeZone.UTC)
        assertEquals(listOf(2, 1, 3, 4, 5), sections.flatMap { it.images }.map { it.id })
        assertNull(sections.last().date)
        assertEquals(2, sections.last().images.size)
    }

    private fun sections() = listOf(
        GallerySection(LocalDate(2026, 10, 9), listOf(photo(1), photo(2), photo(3))),
        GallerySection(LocalDate(2026, 10, 8), listOf(photo(4)))
    )
    private fun geometry(columns: Int = 2, viewport: Int = 200) =
        GalleryScrollGeometry(sections(), columns, 48, 100, 8, 8, 16, viewport)

    @Test fun accountsForFullWidthHeadersAndIncompleteRows() {
        val layout = geometry()
        assertEquals(listOf(0, 1, 3, 4, 5), layout.rows.map { it.firstItem })
        assertEquals(listOf(0, 56, 164, 272, 328), layout.rows.map { it.offset })
        assertEquals(252, layout.maxOffset)
        assertEquals(layout.offsetOf(1, 20), layout.offsetOf(2, 20))
        assertEquals(GalleryScrollTarget(3, 88), layout.target(1f))
        assertEquals(LocalDate(2026, 10, 8), layout.sectionAt(1f)?.date)
    }

    @Test fun dragCanReachBothEndsAfterColumnCountChanges() {
        val layout = geometry(columns = 1)
        assertEquals(360, layout.maxOffset)
        assertEquals(GalleryScrollTarget(0, 0), layout.target(-2f))
        assertEquals(GalleryScrollTarget(3, 88), layout.target(2f))
        for (fraction in listOf(0f, 0.1f, 0.5f, 0.9f, 1f)) {
            val target = layout.target(fraction)
            assertEquals((fraction * layout.maxOffset).toInt(), layout.offsetOf(target.item, target.offset))
        }
    }

    @Test fun emptyAndShortGalleriesNeedNoFastScroll() {
        assertEquals(0, geometry(viewport = 1000).maxOffset)
        val empty = GalleryScrollGeometry(emptyList(), 2, 48, 100, 8, 8, 16, 200)
        assertEquals(0, empty.maxOffset)
        assertEquals(GalleryScrollTarget(0, 0), empty.target(1f))
        assertNull(empty.sectionAt(0.5f))
    }

    @Test fun pinnedDateChangesOnlyWhenTheNextHeaderReachesTheTop() {
        val layout = geometry()
        assertNull(layout.pinnedSection(0, 0))
        assertNull(layout.pinnedSection(0, 7))
        assertEquals(LocalDate(2026, 10, 9), layout.pinnedSection(0, 8)?.date)
        // Use a smaller viewport so the second header can actually reach the top.
        val scrollable = geometry(viewport = 100)
        assertEquals(LocalDate(2026, 10, 9), scrollable.pinnedSection(4, 7)?.date)
        assertEquals(LocalDate(2026, 10, 8), scrollable.pinnedSection(4, 8)?.date)
        assertEquals(LocalDate(2026, 10, 9), scrollable.pinnedSection(4, 7)?.date)
    }

    @Test fun pinnedDateDoesNotDependOnWhichColumnIsReportedFirst() {
        val layout = geometry()
        assertEquals(layout.pinnedSection(1, 20), layout.pinnedSection(2, 20))
        val empty = GalleryScrollGeometry(emptyList(), 2, 48, 100, 8, 8, 16, 200)
        assertNull(empty.pinnedSection(0, 10))
    }
}
