package com.markettwits.waifupics.gallery.items.model

import kotlin.math.abs
import kotlin.test.*

class GalleryScrollbarGeometryTest {
    private fun thumb(progress: Float = 0f, pull: Float = 0f, maxOffset: Float = 20000f,
        track: Float = 600f, viewport: Float = 700f) =
        galleryScrollbarThumb(track, viewport, maxOffset, progress, pull, 36f, 18f)

    private fun close(expected: Float, actual: Float) = assertTrue(abs(expected - actual) < 0.001f,
        "Expected $expected, got $actual")

    @Test fun shortGalleriesMustNotTurnTheHandleIntoAFullHeightScrollBar() {
        for (extent in listOf(1f, 100f, 700f, 20000f, Int.MAX_VALUE.toFloat())) {
            for (viewport in listOf(400f, 700f, 1200f)) {
                close(36f, thumb(maxOffset = extent, viewport = viewport).height)
            }
        }
    }

    @Test fun shortGalleriesStillAllowDraggingAcrossTheWholeTrack() {
        val model = thumb(progress = 0.5f, maxOffset = 1f)
        close(564f, model.travel)
        close(0f, model.progressAfterDrag(0.5f, -model.travel / 2f))
        close(1f, model.progressAfterDrag(0.5f, model.travel / 2f))
    }

    @Test fun compressionKeepsTheCorrectEndpointFixedAtEverySpringFrame() {
        for (pull in listOf(0f, 1f, 12f, 50f, 96f, -2f)) {
            val top = thumb(pull = pull)
            val bottom = thumb(progress = 1f, pull = -pull)
            close(0f, top.top)
            close(600f, bottom.top + bottom.height)
            close(top.height, bottom.height)
            assertTrue(top.height in 18f..36f)
        }
        close(18f, thumb(pull = 100f).height)
        close(36f, thumb(pull = 0f).height)
    }

    @Test fun residualOverscrollDoesNotChangeGeometryInTheMiddleOfTheList() {
        assertEquals(thumb(progress = 0.5f), thumb(progress = 0.5f, pull = 96f))
    }

    @Test fun draggingUsesTheSameLinearTrackInBothDirectionsAndReachesBothEnds() {
        val model = thumb(progress = 0.6f)
        close(0.8f, model.progressAfterDrag(0.6f, model.travel * 0.2f))
        close(0.4f, model.progressAfterDrag(0.6f, -model.travel * 0.2f))
        close(0.6f, model.progressAfterDrag(0.6f, 0f))
        close(0f, model.progressAfterDrag(0.6f, -model.travel))
        close(1f, model.progressAfterDrag(0.6f, model.travel))
    }

    @Test fun thumbNeverOverflowsTheTrackOnSmallLayoutsOrHugePulls() {
        for (track in listOf(1f, 12f, 48f, 600f)) {
            for (progress in listOf(-1f, 0f, 0.1f, 0.9f, 1f, 2f)) {
                for (pull in listOf(0f, 10000f, -10000f)) {
                    val model = thumb(progress, pull, track = track)
                    assertTrue(model.height in 0f..track)
                    assertTrue(model.top >= 0f && model.top + model.height <= track + 0.001f)
                    assertTrue(model.progressAfterDrag(0.5f, 10f).isFinite())
                }
            }
        }
    }

    @Test fun invalidAndNonScrollableViewportsHaveNoThumb() {
        assertEquals(GalleryScrollbarThumb(0f, 0f, 0f), thumb(maxOffset = 0f))
        assertEquals(GalleryScrollbarThumb(0f, 0f, 0f), thumb(track = 0f))
        assertEquals(GalleryScrollbarThumb(0f, 0f, 0f), thumb(viewport = 0f))
    }
}
