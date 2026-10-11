package com.markettwits.theme.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.ClipOp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.math.hypot
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeRevealTest {
    @Test fun darkAreaGrowsOnEntryAndContractsOnExit() {
        val entering = listOf(0f, 0.25f, 0.5f, 0.75f, 1f).map { themeRevealMask(true, it) }
        val leaving = listOf(0f, 0.25f, 0.5f, 0.75f, 1f).map { themeRevealMask(false, it) }
        assertTrue(entering.all { it.clipOp == ClipOp.Difference })
        assertTrue(leaving.all { it.clipOp == ClipOp.Intersect })
        assertEquals(0f, entering.first().radiusFraction)
        assertEquals(1f, entering.last().radiusFraction)
        assertEquals(1f, leaving.first().radiusFraction)
        assertEquals(0f, leaving.last().radiusFraction)
        entering.zipWithNext().forEach { (before, after) -> assertTrue(after.radiusFraction > before.radiusFraction) }
        leaving.zipWithNext().forEach { (before, after) -> assertTrue(after.radiusFraction < before.radiusFraction) }
    }

    @Test fun revealHasSymmetricAccelerationAndExactEndpoints() {
        assertEquals(0f, ThemeRevealEasing.transform(0f))
        assertEquals(0.5f, ThemeRevealEasing.transform(0.5f))
        assertEquals(1f, ThemeRevealEasing.transform(1f))
        assertTrue(ThemeRevealEasing.transform(0.25f) < 0.25f)
        assertEquals(1f - ThemeRevealEasing.transform(0.25f), ThemeRevealEasing.transform(0.75f))
    }

    @Test fun revealDirectionIsCapturedBeforeTheThemeFlowChanges() = runTest {
        val state = ThemeRevealState(this)
        val captured = CompletableDeferred<Unit>()
        state.captureFrame = { captured.await(); error("Capture unavailable") }
        state.observeTheme(false)
        state.changeTheme(null) { }
        state.observeTheme(true)
        assertTrue(state.targetDark)
        captured.complete(Unit)
        runCurrent()
        state.changeTheme(null) { }
        state.observeTheme(false)
        assertFalse(state.targetDark)
        runCurrent()
    }

    @Test fun radiusCoversEveryCornerForOffCenterButton() {
        val size = Size(1080f, 2400f)
        val center = Offset(72f, 380f)
        val radius = themeRevealRadius(center, size)
        listOf(Offset.Zero, Offset(size.width, 0f), Offset(0f, size.height),
            Offset(size.width, size.height)).forEach { corner ->
            assertTrue(radius >= hypot(corner.x - center.x, corner.y - center.y))
        }
        assertEquals(hypot(1008f, 2020f), radius)
    }

    @Test fun missingOrInvalidButtonUsesScreenCenterAndOffscreenButtonIsClamped() {
        val size = Size(400f, 800f)
        assertEquals(size.center, themeRevealOrigin(null, size))
        assertEquals(size.center, themeRevealOrigin(Offset(Float.NaN, 20f), size))
        assertEquals(Offset(0f, 800f), themeRevealOrigin(Offset(-30f, 900f), size))
    }

    @Test fun rapidTapsCannotPersistBeforeCaptureOrEnqueueExtraChanges() = runTest {
        val state = ThemeRevealState(this)
        val captureReady = CompletableDeferred<Unit>()
        var changes = 0
        state.captureFrame = {
            captureReady.await()
            error("Capture unavailable on this surface")
        }
        state.changeTheme(Offset.Zero) { changes++ }
        state.changeTheme(Offset.Zero) { changes++ }
        assertTrue(state.isAnimating)
        runCurrent()
        assertEquals(0, changes)
        captureReady.complete(Unit)
        runCurrent()
        assertEquals(1, changes)
        assertFalse(state.isAnimating)
    }

    @Test fun stalledCaptureDoesNotPreventThemeSwitching() = runTest {
        val state = ThemeRevealState(this)
        var changes = 0
        state.captureFrame = {
            CompletableDeferred<Unit>().await()
            error("Unreachable")
        }
        state.changeTheme(null) { changes++ }
        runCurrent()
        advanceTimeBy(1_000)
        runCurrent()
        assertEquals(1, changes)
        assertFalse(state.isAnimating)
    }

    @Test fun unattachedHostStillAllowsChangingTheme() = runTest {
        val state = ThemeRevealState(this)
        var changes = 0
        state.changeTheme(null) { changes++ }
        assertEquals(1, changes)
        assertFalse(state.isAnimating)
    }
}
