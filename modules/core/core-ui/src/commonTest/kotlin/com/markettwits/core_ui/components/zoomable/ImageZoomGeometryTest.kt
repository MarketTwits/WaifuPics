package com.markettwits.core_ui.components.zoomable

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.test.*

class ImageZoomGeometryTest {
    private fun geometry(viewport: Size = Size(100f, 200f), image: Size = Size(300f, 300f), limit: Float = 8f) =
        ImageZoomGeometry.calculate(viewport, image, limit)!!

    @Test fun usesImageAndViewportPixelsInsteadOfAFixedMultiplier() {
        val square = geometry()
        assertEquals(2f, square.coverScale)
        assertEquals(3f, square.originalScale)
        val portrait = geometry(image = Size(300f, 1200f))
        assertEquals(2f, portrait.coverScale)
        assertEquals(6f, portrait.originalScale)
        val landscape = geometry(image = Size(600f, 200f))
        assertEquals(6f, landscape.coverScale)
        assertEquals(6f, landscape.originalScale)
    }

    @Test fun cyclesFromFitThroughCoverAndOriginalBackToFit() {
        val geometry = geometry()
        assertEquals(ImageZoomStage.Cover, geometry.nextStage(ImageZoomStage.Fit, 1f))
        assertEquals(ImageZoomStage.Original, geometry.nextStage(ImageZoomStage.Cover, 2f))
        assertEquals(ImageZoomStage.Fit, geometry.nextStage(ImageZoomStage.Original, 3f))
    }

    @Test fun skipsCoverWhenTheImageAlreadyFillsTheViewport() {
        val geometry = geometry(image = Size(300f, 600f))
        assertEquals(1f, geometry.coverScale)
        assertEquals(ImageZoomStage.Original, geometry.nextStage(ImageZoomStage.Fit, 1f))
    }

    @Test fun skipsOriginalWhenItMatchesCover() {
        val geometry = geometry(image = Size(200f, 200f))
        assertEquals(geometry.coverScale, geometry.originalScale)
        assertEquals(ImageZoomStage.Fit, geometry.nextStage(ImageZoomStage.Cover, 2f))
    }

    @Test fun doesNotInventExtraMagnificationForSmallOrExactlyFittingImages() {
        for (image in listOf(Size(50f, 100f), Size(100f, 200f))) {
            val geometry = geometry(image = image)
            assertEquals(1f, geometry.originalScale)
            assertNull(geometry.nextStage(ImageZoomStage.Fit, 1f))
        }
        val smallSquare = geometry(image = Size(50f, 50f))
        assertEquals(2f, smallSquare.coverScale)
        assertEquals(1f, smallSquare.originalScale)
        assertEquals(ImageZoomStage.Original, smallSquare.nextStage(ImageZoomStage.Cover, 2f))
        assertEquals(ImageZoomStage.Cover, smallSquare.nextStage(ImageZoomStage.Original, 1f))
    }

    @Test fun respectsMaximumZoomAndSkipsClampedDuplicateStages() {
        val geometry = geometry(image = Size(1000f, 1000f), limit = 2f)
        assertEquals(2f, geometry.coverScale)
        assertEquals(2f, geometry.originalScale)
        assertEquals(ImageZoomStage.Fit, geometry.nextStage(ImageZoomStage.Cover, 2f))
    }

    @Test fun manualZoomOrPanReturnsToFit() {
        assertEquals(ImageZoomStage.Fit, geometry().nextStage(ImageZoomStage.Gesture, 2.3f))
    }

    @Test fun ignoresMissingAndInvalidGeometry() {
        for (invalid in listOf(Size.Zero, Size(-1f, 100f), Size(Float.NaN, 100f), Size(100f, Float.POSITIVE_INFINITY))) {
            assertNull(ImageZoomGeometry.calculate(invalid, Size(300f, 300f), 8f))
            assertNull(ImageZoomGeometry.calculate(Size(100f, 200f), invalid, 8f))
        }
    }

    @Test fun retainsTheSameImagePointUnderAnOffCenterTapEvenAfterPanning() {
        val viewport = Size(100f, 200f)
        val center = Offset(50f, 100f)
        val tap = Offset(70f, 135f)
        val oldOffset = Offset(-8f, 15f)
        val oldScale = 2f
        val target = 3f
        val newOffset = imageZoomOffset(viewport, oldOffset, oldScale, target, tap)
        val imagePointBefore = (tap - center - oldOffset) / oldScale
        val imagePointAfter = (tap - center - newOffset) / target
        assertEquals(imagePointBefore.x, imagePointAfter.x, 0.0001f)
        assertEquals(imagePointBefore.y, imagePointAfter.y, 0.0001f)
        assertEquals(newOffset + Offset(4f, 7f), imageZoomOffset(viewport, oldOffset, oldScale, target, tap, Offset(4f, 7f)))
    }
}
