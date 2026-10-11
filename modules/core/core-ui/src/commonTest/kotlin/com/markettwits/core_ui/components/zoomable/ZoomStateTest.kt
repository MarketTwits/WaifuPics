package com.markettwits.core_ui.components.zoomable

import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withContext
import kotlin.test.*

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ZoomStateTest {
    private class FrameClock : MonotonicFrameClock {
        private var time = 0L
        override suspend fun <R> withFrameNanos(onFrame: (Long) -> R): R {
            delay(16)
            time += 16_000_000
            return onFrame(time)
        }
    }

    private fun state(image: Size = Size(300f, 300f)) = ZoomState(8f, image).apply {
        setLayoutSize(Size(100f, 200f))
    }

    @Test fun animatesAllStagesAndCentersTheFinalFit() = runTest {
        val state = state()
        withContext(FrameClock()) {
            val tap = Offset(70f, 130f)
            state.zoomOnDoubleTap(tap)
            assertEquals(2f, state.scale, 0.001f)
            assertEquals(-20f, state.offsetX, 0.001f)
            assertEquals(0f, state.offsetY, 0.001f) // Cover must not expose a gap at an edge.
            state.zoomOnDoubleTap(tap)
            assertEquals(3f, state.scale, 0.001f)
            assertEquals(-40f, state.offsetX, 0.001f)
            assertEquals(-15f, state.offsetY, 0.001f)
            state.zoomOnDoubleTap(tap)
            assertEquals(1f, state.scale, 0.001f)
            assertEquals(0f, state.offsetX, 0.001f)
            assertEquals(0f, state.offsetY, 0.001f)
        }
    }

    @Test fun aPinchOrPanChangesTheNextDoubleTapIntoReset() = runTest {
        for (zoom in listOf(1f, 1.1f)) {
            val state = state()
            withContext(FrameClock()) {
                state.zoomOnDoubleTap(Offset(50f, 100f))
                state.startGesture()
                state.applyGesture(Offset(5f, 0f), zoom, Offset(50f, 100f), 10L)
                state.zoomOnDoubleTap(Offset(20f, 30f))
                assertEquals(1f, state.scale, 0.001f)
                assertEquals(0f, state.offsetX, 0.001f)
                assertEquals(0f, state.offsetY, 0.001f)
            }
        }
    }

    @Test fun resetAlsoRestartsTheDoubleTapCycle() = runTest {
        val state = state()
        withContext(FrameClock()) {
            state.zoomOnDoubleTap(Offset(50f, 100f))
            state.reset()
            state.zoomOnDoubleTap(Offset(50f, 100f))
            assertEquals(2f, state.scale, 0.001f)
        }
    }

    @Test fun missingContentIsSafeAndDoesNotStartAnAnimation() = runTest {
        val state = state(Size.Zero)
        state.zoomOnDoubleTap(Offset(50f, 100f))
        assertEquals(1f, state.scale)
        state.applyGesture(Offset.Zero, 1.5f, Offset(50f, 100f), 10L)
        assertTrue(state.offsetX.isFinite() && state.offsetY.isFinite())
    }

    @Test fun rapidDoubleTapsAndNewGesturesCancelAllOldAnimationAxes() = runTest {
        val state = state()
        val first = launch(FrameClock()) { state.zoomOnDoubleTap(Offset(70f, 130f)) }
        advanceTimeBy(80)
        runCurrent()
        assertTrue(state.scale > 1f && state.scale < 2f)
        state.startGesture()
        val interrupted = state.scale
        first.join()
        advanceTimeBy(500)
        assertEquals(interrupted, state.scale)
        withContext(FrameClock()) {
            state.zoomOnDoubleTap(Offset(70f, 130f))
            assertEquals(3f, state.scale, 0.001f)
        }
        val reset = launch(FrameClock()) { state.changeScale(1f, Offset.Zero) }
        advanceTimeBy(64)
        runCurrent()
        state.startGesture()
        state.applyGesture(Offset.Zero, 1.1f, Offset(50f, 100f), 10L)
        val pinched = state.scale
        reset.join()
        advanceTimeBy(1000)
        assertEquals(pinched, state.scale)
        withContext(FrameClock()) {
            state.zoomOnDoubleTap(Offset(70f, 130f))
            assertEquals(1f, state.scale, 0.001f)
        }
    }

    @Test fun viewportChangesReturnManualZoomToFitBeforeRestartingTheCycle() = runTest {
        val state = state()
        withContext(FrameClock()) {
            state.zoomOnDoubleTap(Offset(50f, 100f))
            state.setLayoutSize(Size(200f, 100f))
            state.zoomOnDoubleTap(Offset(100f, 50f))
            assertEquals(1f, state.scale, 0.001f)
            state.zoomOnDoubleTap(Offset(100f, 50f))
            assertEquals(2f, state.scale, 0.001f)
        }
    }
}
