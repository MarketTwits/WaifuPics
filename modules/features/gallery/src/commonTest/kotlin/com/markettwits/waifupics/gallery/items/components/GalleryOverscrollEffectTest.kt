package com.markettwits.waifupics.gallery.items.components

import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class GalleryOverscrollEffectTest {
    private class Edges(var backward: Boolean = true, var forward: Boolean = true) {
        val effect = GalleryOverscrollEffect(96f, 2500f, { backward }, { forward })
    }

    private class FrameClock : MonotonicFrameClock {
        private var time = 0L
        override suspend fun <R> withFrameNanos(onFrame: (Long) -> R): R {
            delay(16)
            time += 16_000_000
            return onFrame(time)
        }
    }

    @Test fun partialConsumptionInTheMiddleDoesNotCreateOrConsumeOverscroll() {
        val effect = Edges().effect
        for (delta in listOf(300f, -300f, 0.1f, -0.1f)) {
            val input = Offset(12f, delta)
            val consumed = effect.applyToScroll(input, NestedScrollSource.UserInput) {
                it.copy(y = it.y * 0.8f)
            }
            assertEquals(input.copy(y = delta * 0.8f), consumed)
            assertEquals(0f, effect.displacement)
            assertFalse(effect.isInProgress)
        }
    }

    @Test fun residualFlingVelocityInTheMiddleNeverStartsASpring() = runTest {
        val effect = Edges().effect
        for (speed in listOf(8000f, -8000f, 0.5f, -0.5f)) {
            var calls = 0
            val input = Velocity(0f, speed)
            effect.applyToFling(input) {
                calls++
                assertEquals(input, it)
                Velocity.Zero
            }
            assertEquals(1, calls)
            assertEquals(0f, effect.displacement)
            assertFalse(effect.isInProgress)
        }
    }

    @Test fun pullsOnlyInTheOutwardDirectionAtEachActualEdge() {
        val edges = Edges(backward = false)
        val effect = edges.effect
        effect.applyToScroll(Offset(0f, -20f), NestedScrollSource.UserInput) { Offset.Zero }
        assertEquals(0f, effect.displacement)
        assertEquals(Offset(0f, 20f), effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero })
        assertEquals(9f, effect.displacement)
        effect.reset()
        edges.backward = true
        edges.forward = false
        effect.applyToScroll(Offset(0f, -20f), NestedScrollSource.UserInput) { Offset.Zero }
        assertEquals(-9f, effect.displacement)
    }

    @Test fun returningFromAPullReleasesTensionBeforeScrollingTheContent() {
        val effect = Edges(backward = false).effect
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        var passedToContent = Offset.Zero
        val consumed = effect.applyToScroll(Offset(0f, -30f), NestedScrollSource.UserInput) {
            passedToContent = it
            it
        }
        assertEquals(Offset(0f, -10f), passedToContent)
        assertEquals(Offset(0f, -30f), consumed)
        assertEquals(0f, effect.displacement)
    }

    @Test fun movingAwayFromTheEdgeClearsAPullAndPreservesTheNextFling() = runTest {
        val edges = Edges(backward = false)
        val effect = edges.effect
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        edges.backward = true
        val input = Velocity(0f, -3000f)
        effect.applyToFling(input) {
            assertEquals(input, it)
            it
        }
        assertEquals(0f, effect.displacement)
        assertFalse(effect.isInProgress)
    }

    @Test fun programmaticScrollAwayFromAnEdgeClearsExistingDisplacement() {
        val edges = Edges(backward = false)
        val effect = edges.effect
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        effect.applyToScroll(Offset(0f, -40f), NestedScrollSource.SideEffect) {
            edges.backward = true
            it
        }
        assertEquals(0f, effect.displacement)
    }

    @Test fun completingAnOldFlingCannotOverrideANewGesture() = runTest {
        val edges = Edges()
        val effect = edges.effect
        val entered = CompletableDeferred<Unit>()
        val finish = CompletableDeferred<Unit>()
        val oldFling = launch {
            effect.applyToFling(Velocity(0f, 8000f)) {
                entered.complete(Unit)
                finish.await()
                Velocity.Zero
            }
        }
        entered.await()
        edges.backward = false
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        finish.complete(Unit)
        oldFling.join()
        assertEquals(9f, effect.displacement)
        effect.reset()
    }

    @Test fun cancellingTheFlingCancelsAndClearsItsSpring() = runTest {
        val effect = Edges(backward = false).effect
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        val fling = launch(FrameClock()) {
            effect.applyToFling(Velocity.Zero) { it }
        }
        runCurrent()
        assertTrue(effect.isInProgress)
        fling.cancelAndJoin()
        advanceTimeBy(1000)
        runCurrent()
        assertEquals(0f, effect.displacement)
        assertFalse(effect.isInProgress)
    }

    @Test fun interruptingASpringKeepsTheNewPullAndNeverMovesAcrossTheEdge() = runTest {
        val effect = Edges(backward = false).effect
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        val oldFling = launch(FrameClock()) {
            effect.applyToFling(Velocity(0f, 1000f)) { Velocity.Zero }
        }
        advanceTimeBy(80)
        runCurrent()
        assertTrue(effect.displacement in 0f..96f)
        effect.applyToScroll(Offset(0f, 20f), NestedScrollSource.UserInput) { Offset.Zero }
        val newPull = effect.displacement
        oldFling.join()
        assertEquals(newPull, effect.displacement)
        val release = launch(FrameClock()) { effect.applyToFling(Velocity.Zero) { it } }
        while (release.isActive) {
            advanceTimeBy(16)
            runCurrent()
            assertTrue(effect.displacement in 0f..96f)
        }
        assertEquals(0f, effect.displacement)
    }
}
