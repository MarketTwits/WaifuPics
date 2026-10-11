package com.markettwits.waifupics.gallery.items.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import com.markettwits.waifupics.gallery.items.model.GalleryScrollGeometry
import com.markettwits.waifupics.gallery.items.model.galleryScrollbarThumb
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*
import kotlin.math.roundToInt

private class ScrollbarRequest(val geometry: GalleryScrollGeometry, val progress: Float)

/** Paint and pointer input use one fixed rail; moving dates never move the thumb. */
@Composable
internal fun GalleryFastScroller(state: LazyGridState, geometry: GalleryScrollGeometry,
    modifier: Modifier = Modifier, overscroll: GalleryOverscrollEffect? = null, pinnedHeaderHeight: Dp = 0.dp) {
    if (geometry.maxOffset <= 0) return
    val actualProgress by remember(state, geometry) {
        derivedStateOf {
            when {
                !state.canScrollBackward -> 0f
                !state.canScrollForward -> 1f
                else -> (geometry.offsetOf(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
                    .toFloat() / geometry.maxOffset).coerceIn(0f, 1f)
            }
        }
    }
    var dragging by remember { mutableStateOf(false) }
    var dragProgress by remember { mutableFloatStateOf(0f) }
    var request by remember { mutableStateOf<ScrollbarRequest?>(null) }
    LaunchedEffect(state, geometry) {
        snapshotFlow { request }.collectLatest { command ->
            if (command != null) {
                try {
                    if (command.geometry === geometry) {
                        val target = geometry.target(command.progress)
                        state.scrollToItem(target.item, target.offset)
                    }
                } finally {
                    // A grid gesture may interrupt scrolling, but must not leave a stale thumb.
                    // A completed/cancelled old command must not clear a more recent drag position.
                    if (request === command) request = null
                }
            }
        }
    }
    val displayedProgress = if (dragging) dragProgress else
        request?.takeIf { it.geometry === geometry }?.progress ?: actualProgress
    val period = galleryPeriodLabel(geometry.sectionAt(displayedProgress)?.date)
    val description = stringResource(Res.string.gallery_fast_scroll)
    val thumbWidth by animateDpAsState(if (dragging) 10.dp else 6.dp, label = "Gallery scroll handle width")
    val haptic = LocalHapticFeedback.current
    val seek: (Float) -> Unit = { value ->
        if (value.isFinite()) {
            val progress = value.coerceIn(0f, 1f)
            val previous = if (dragging) dragProgress else
                request?.takeIf { it.geometry === geometry }?.progress ?: actualProgress
            if (geometry.sectionAt(previous)?.key != geometry.sectionAt(progress)?.key) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
            dragProgress = progress
            request = ScrollbarRequest(geometry, progress)
        }
    }
    BoxWithConstraints(modifier.fillMaxWidth().shareScrollbarPointerInput()) {
        val density = LocalDensity.current
        val trackHeight = with(density) { maxHeight.toPx() }
        val hitSize = with(density) { WaifuSize.touchTarget.toPx() }
        val info = state.layoutInfo
        if (trackHeight < hitSize || info.totalItemsCount == 0) return@BoxWithConstraints
        val viewport = info.viewportSize.height.toFloat()
        if (viewport <= 0f) return@BoxWithConstraints
        val displacement = overscroll?.displacement ?: 0f
        val normalThumb = with(density) {
            galleryScrollbarThumb(trackHeight, viewport, geometry.maxOffset.toFloat(), displayedProgress,
                0f, 36.dp.toPx(), 18.dp.toPx())
        }
        val thumb = with(density) {
            galleryScrollbarThumb(trackHeight, viewport, geometry.maxOffset.toFloat(), displayedProgress,
                if (dragging) 0f else displacement, 36.dp.toPx(), 18.dp.toPx())
        }
        val hitHeight = maxOf(hitSize, thumb.height).coerceAtMost(trackHeight)
        val hitTop = (thumb.center - hitHeight / 2f).coerceIn(0f, trackHeight - hitHeight)
        val touchInset = with(density) { WaifuSpacing.content.toPx() }
        val dateSlop = with(density) { 4.dp.toPx() }
        val pinnedHeight = with(density) { pinnedHeaderHeight.toPx() }
        val headers = (if (pinnedHeight > 0f) listOf(-dateSlop..(pinnedHeight + dateSlop)) else emptyList()) +
            info.visibleItemsInfo.filter { it.key.toString().startsWith("date-") }.map {
            val top = it.offset.y - info.viewportStartOffset - info.beforeContentPadding + displacement
            (top - dateSlop)..(top + it.size.height + dateSlop)
        }
        val latestSeek by rememberUpdatedState(seek)
        val latestProgress by rememberUpdatedState(displayedProgress)
        val latestThumb by rememberUpdatedState(normalThumb)
        val latestHitTop by rememberUpdatedState(hitTop)
        val latestHitHeight by rememberUpdatedState(hitHeight)
        val latestHeaders by rememberUpdatedState(headers)
        val latestOverscroll by rememberUpdatedState(overscroll)
        val color = MaterialTheme.colorScheme.primary.copy(alpha = if (dragging) 1f else 0.65f)

        if (dragging) {
            Surface(Modifier.align(Alignment.TopEnd).padding(end = 52.dp)
                .offset { IntOffset(0, hitTop.roundToInt()) }, shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface, shadowElevation = 2.dp) {
                Text(period, Modifier.padding(horizontal = WaifuSpacing.item, vertical = WaifuSpacing.small),
                    style = MaterialTheme.typography.labelLarge, maxLines = 1)
            }
        }
        // Semantics follow the enlarged thumb, rather than describing the entire track as a button.
        Box(Modifier.align(Alignment.TopEnd).offset { IntOffset(0, hitTop.roundToInt()) }
            .width(WaifuSize.touchTarget).height(with(density) { hitHeight.toDp() }).semantics {
                contentDescription = description
                stateDescription = period
                progressBarRangeInfo = ProgressBarRangeInfo(displayedProgress, 0f..1f)
                setProgress { overscroll?.reset(); seek(it); true }
            })
        Canvas(Modifier.align(Alignment.TopEnd).width(WaifuSize.touchTarget).fillMaxHeight()
            .scrollbarPointerInput(geometry to trackHeight) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
                    if (down.isConsumed || down.position.y !in latestHitTop..(latestHitTop + latestHitHeight)) {
                        return@awaitEachGesture
                    }
                    // The outer rail owns its thumb; the inner part lets date controls receive the tap.
                    if (down.position.x < size.width - touchInset &&
                        latestHeaders.any { down.position.y in it }) return@awaitEachGesture
                    val startY = down.position.y
                    val startProgress = latestProgress
                    val dragThumb = latestThumb
                    latestOverscroll?.reset()
                    down.consume()
                    dragProgress = startProgress
                    dragging = true
                    // Hold any existing grid fling without moving the thumb away from the finger.
                    latestSeek(startProgress)
                    try {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            if (event.changes.count { it.pressed } > 1) break
                            val change = event.changes.firstOrNull { it.id == down.id } ?: break
                            latestSeek(dragThumb.progressAfterDrag(startProgress, change.position.y - startY))
                            change.consume()
                            if (!change.pressed) break
                        }
                    } finally {
                        dragging = false
                    }
                }
            }) {
            val width = thumbWidth.toPx()
            drawRoundRect(color, Offset(size.width - 4.dp.toPx() - width, thumb.top),
                Size(width, thumb.height), CornerRadius(width / 2f))
        }
    }
}
