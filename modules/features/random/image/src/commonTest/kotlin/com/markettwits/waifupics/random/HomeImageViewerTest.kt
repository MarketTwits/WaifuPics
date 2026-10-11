package com.markettwits.waifupics.random

import androidx.compose.ui.geometry.Size
import com.markettwits.waifupics.random.components.image_state.full_screen.fitHomeImage
import com.markettwits.waifupics.random.components.image_state.full_screen.shouldDismissHomeImage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HomeImageViewerTest {
    @Test fun artworkBoundsKeepPortraitAndLandscapeComposition() {
        assertEquals(Size(300f, 600f), fitHomeImage(Size(1000f, 2000f), Size(400f, 600f)))
        assertEquals(Size(400f, 200f), fitHomeImage(Size(2000f, 1000f), Size(400f, 600f)))
        assertEquals(Size(400f, 600f), fitHomeImage(Size(800f, 1200f), Size(400f, 600f)))
    }

    @Test fun previewAndOriginalResolutionShareExactlyTheSameBounds() {
        val viewport = Size(1080f, 1920f)
        assertEquals(fitHomeImage(Size(400f, 600f), viewport), fitHomeImage(Size(1600f, 2400f), viewport))
    }

    @Test fun unavailableSizesNeverProduceInvalidLayoutDimensions() {
        assertEquals(Size(400f, 600f), fitHomeImage(Size.Unspecified, Size(400f, 600f)))
        assertEquals(Size(400f, 600f), fitHomeImage(Size.Zero, Size(400f, 600f)))
        assertEquals(Size.Zero, fitHomeImage(Size(400f, 600f), Size.Zero))
    }

    @Test fun aShortDragOrReversingFlingReturnsToViewer() {
        assertFalse(dismiss(31f, 2000f))
        assertFalse(dismiss(90f, 1100f))
        assertFalse(dismiss(90f, -2000f))
        assertFalse(dismiss(-90f, 2000f))
    }

    @Test fun deliberateDragAndSameDirectionFlingDismissInEitherDirection() {
        assertTrue(dismiss(120f, 0f))
        assertTrue(dismiss(-120f, 0f))
        assertTrue(dismiss(40f, 1300f))
        assertTrue(dismiss(-40f, -1300f))
    }

    private fun dismiss(offset: Float, velocity: Float) = shouldDismissHomeImage(offset, velocity, 120f, 32f, 1200f)
}
