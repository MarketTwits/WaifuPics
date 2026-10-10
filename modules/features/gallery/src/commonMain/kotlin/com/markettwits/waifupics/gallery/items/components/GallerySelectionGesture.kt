package com.markettwits.waifupics.gallery.items.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.items.model.GallerySelectionRange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private fun LazyGridState.photoAt(point: Offset, horizontalInset: Float, clampToContent: Boolean = false, obscuredTop: Float = 0f): Int? {
    val info = layoutInfo
    val bottom = (info.viewportSize.height - info.afterContentPadding - 1).coerceAtLeast(1).toFloat()
    val headerBottom = info.visibleItemsInfo.filter { it.key.toString().startsWith("date-") && it.offset.y <= 0 }
        .maxOfOrNull { it.offset.y + it.size.height - info.viewportStartOffset } ?: 0
    val top = maxOf((headerBottom + info.mainAxisItemSpacing + 1).toFloat(), obscuredTop + 1f).coerceIn(1f, bottom)
    val y = if (clampToContent) point.y.coerceIn(top, bottom) else point.y
    // Item offsets exclude content padding; pointer coordinates include the full grid viewport.
    val position = Offset(point.x - horizontalInset, y + info.viewportStartOffset)
    fun contains(offset: androidx.compose.ui.unit.IntOffset, size: androidx.compose.ui.unit.IntSize) =
        position.x >= offset.x && position.x < offset.x + size.width &&
            position.y >= offset.y && position.y < offset.y + size.height
    if (info.visibleItemsInfo.any { it.key.toString().startsWith("date-") && contains(it.offset, it.size) }) return null
    return info.visibleItemsInfo.firstOrNull {
        it.key.toString().startsWith("photo-") && contains(it.offset, it.size)
    }?.key?.toString()?.removePrefix("photo-")?.toIntOrNull()
}

/** In selection mode a swipe changes a range; otherwise a long press starts the same gesture. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Modifier.gallerySelectionGesture(
    state: LazyGridState,
    ids: List<Int>,
    selectedIds: Set<Int>,
    selectionMode: Boolean,
    onSelection: (Set<Int>) -> Unit,
    onFinish: () -> Unit,
    viewportTranslationY: () -> Float = { 0f },
    obscuredTop: () -> Float = { 0f }
): Modifier {
    val currentIds by rememberUpdatedState(ids)
    val currentSelection by rememberUpdatedState(selectedIds)
    val currentMode by rememberUpdatedState(selectionMode)
    val changeSelection by rememberUpdatedState(onSelection)
    val finishSelection by rememberUpdatedState(onFinish)
    val translationY by rememberUpdatedState(viewportTranslationY)
    val currentObscuredTop by rememberUpdatedState(obscuredTop)
    val haptic = LocalHapticFeedback.current
    return pointerInput(state, ids) {
        val horizontalInset = WaifuSpacing.content.toPx()
        coroutineScope {
            val gestureScope = this
            fun photoAt(point: Offset, clampToContent: Boolean = false): Int? {
                if (!clampToContent && point.y < currentObscuredTop()) return null
                val shift = translationY()
                return state.photoAt(point - Offset(0f, shift), horizontalInset, clampToContent,
                    obscuredTop = (currentObscuredTop() - shift).coerceAtLeast(0f))
            }
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
                if (down.isConsumed) return@awaitEachGesture
                val anchor = photoAt(down.position) ?: return@awaitEachGesture
                val startedInSelectionMode = currentMode
                val baseline = currentSelection.toSet()
                var position = down.position
                if (startedInSelectionMode) {
                    var activated = false
                    while (!activated) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if (event.changes.count { it.pressed } > 1) return@awaitEachGesture
                        val change = event.changes.firstOrNull { it.id == down.id } ?: return@awaitEachGesture
                        if (!change.pressed) return@awaitEachGesture
                        position = change.position
                        activated = (position - down.position).getDistance() >= viewConfiguration.touchSlop
                        if (activated) change.consume()
                    }
                } else {
                    // Finish the down event before waiting on Main: the image consumes its
                    // down there, which would otherwise cancel long press immediately.
                    awaitPointerEvent(PointerEventPass.Final)
                    val press = awaitLongPressOrCancellation(down.id) ?: return@awaitEachGesture
                    position = press.position
                    press.consume()
                }
                val range = GallerySelectionRange(currentIds.toList(), baseline, anchor,
                    selecting = !startedInSelectionMode || anchor !in baseline)
                var lastId: Int? = null
                fun update(id: Int?) {
                    if (id != null && id != lastId) {
                        changeSelection(range.selectionAt(id))
                        if (lastId != null) haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        lastId = id
                    }
                }
                update(anchor)
                haptic.performHapticFeedback(if (startedInSelectionMode) HapticFeedbackType.TextHandleMove
                    else HapticFeedbackType.LongPress)
                update(photoAt(position))
                val autoScroll = gestureScope.launch {
                    var previousFrame = 0L
                    while (isActive) {
                        val frame = withFrameNanos { it }
                        val seconds = if (previousFrame == 0L) 0f else ((frame - previousFrame) / 1_000_000_000f).coerceAtMost(0.05f)
                        previousFrame = frame
                        val info = state.layoutInfo
                        val edge = 56 * density
                        val bottom = (info.viewportSize.height - info.afterContentPadding).coerceAtLeast(1).toFloat()
                        val top = currentObscuredTop()
                        val speed = when {
                            position.y < top + edge -> -((top + edge - position.y) / edge).coerceIn(0f, 1f)
                            position.y > bottom - edge -> ((position.y - (bottom - edge)) / edge).coerceIn(0f, 1f)
                            else -> 0f
                        }
                        if (speed != 0f) {
                            state.scrollBy(speed * 800 * density * seconds)
                            update(photoAt(position, clampToContent = true))
                        }
                    }
                }
                try {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if (event.changes.count { it.pressed } > 1) break
                        val change = event.changes.firstOrNull { it.id == down.id } ?: break
                        position = change.position
                        update(photoAt(position, clampToContent = true))
                        change.consume()
                        if (!change.pressed) break
                    }
                } finally {
                    autoScroll.cancel()
                    finishSelection()
                }
            }
        }
    }
}
