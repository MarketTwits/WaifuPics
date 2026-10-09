package com.markettwits.waifupics.gallery.items.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import com.markettwits.waifupics.gallery.items.model.GalleryScrollGeometry
import kotlinx.coroutines.channels.Channel
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*
import kotlin.math.roundToInt
import kotlin.math.abs

/** Only the handle intercepts touches; the rest of the photo column stays interactive. */
@OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun GalleryFastScroller(state: LazyGridState, geometry: GalleryScrollGeometry, modifier: Modifier = Modifier) {
    if (geometry.maxOffset <= 0) return
    val actualProgress by remember(state, geometry) {
        derivedStateOf {
            when {
                !state.canScrollBackward -> 0f
                !state.canScrollForward -> 1f
                else -> geometry.offsetOf(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
                    .toFloat() / geometry.maxOffset
            }
        }
    }
    var dragging by remember { mutableStateOf(false) }
    var thumbCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    var dragProgress by remember { mutableFloatStateOf(0f) }
    var pendingProgress by remember { mutableStateOf<Float?>(null) }
    val requests = remember { Channel<Float>(Channel.CONFLATED) }
    DisposableEffect(requests) { onDispose { requests.close() } }
    LaunchedEffect(state, geometry) {
        pendingProgress?.let { requests.trySend(it) }
        for (progress in requests) {
            val target = geometry.target(progress)
            state.scrollToItem(target.item, target.offset)
            if (pendingProgress == progress) pendingProgress = null
        }
    }
    val displayedProgress = (if (dragging) dragProgress else pendingProgress ?: actualProgress).coerceIn(0f, 1f)
    val period = galleryPeriodLabel(geometry.sectionAt(displayedProgress)?.date)
    val description = stringResource(Res.string.gallery_fast_scroll)
    val thumbWidth by animateDpAsState(if (dragging) 10.dp else 6.dp, label = "Gallery scroll handle width")
    val haptic = LocalHapticFeedback.current
    val seek: (Float) -> Unit = { value ->
        if (value.isFinite()) {
            val progress = value.coerceIn(0f, 1f)
            val previous = if (dragging) dragProgress else pendingProgress ?: actualProgress
            if (geometry.sectionAt(previous)?.key != geometry.sectionAt(progress)?.key) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
            dragProgress = progress
            pendingProgress = progress
            requests.trySend(progress)
        }
    }
    BoxWithConstraints(modifier.fillMaxWidth()) {
        if (maxHeight < WaifuSize.touchTarget) return@BoxWithConstraints
        val density = LocalDensity.current
        val travel = with(density) { (maxHeight - WaifuSize.touchTarget).toPx() }.coerceAtLeast(1f)
        val desiredTop = (displayedProgress * travel).roundToInt()
        val thumbHeight = with(density) { WaifuSize.touchTarget.roundToPx() }
        val info = state.layoutInfo
        val clearance = with(density) { 4.dp.roundToPx() }
        val headers = info.visibleItemsInfo.filter { it.key.toString().startsWith("date-") }.map {
            val start = it.offset.y - info.viewportStartOffset - info.beforeContentPadding
            start to start + it.size.height
        }
        // Keep the full touch target away from date controls, including their vertical hit slop.
        val candidates = listOf(desiredTop) + headers.flatMap { (start, end) ->
            listOf(start - thumbHeight - clearance, end + clearance)
        }
        val top = candidates.map { it.coerceIn(0, travel.toInt()) }.filter { candidate ->
            headers.none { (start, end) -> candidate < end + clearance && candidate + thumbHeight > start - clearance }
        }.minByOrNull { abs(it - desiredTop) } ?: desiredTop
        val latestSeek by rememberUpdatedState(seek)
        val latestProgress by rememberUpdatedState(displayedProgress)
        val latestTop by rememberUpdatedState(top)
        if (dragging) {
            Surface(Modifier.align(Alignment.TopEnd).padding(end = 52.dp)
                .offset { IntOffset(0, top) }, shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface, shadowElevation = 2.dp) {
                Text(period, Modifier.padding(horizontal = WaifuSpacing.item, vertical = WaifuSpacing.small),
                    style = MaterialTheme.typography.labelLarge, maxLines = 1)
            }
        }
        Box(Modifier.align(Alignment.TopEnd).offset { IntOffset(0, top) }
            .size(WaifuSize.touchTarget)
            .semantics {
                contentDescription = description
                stateDescription = period
                progressBarRangeInfo = ProgressBarRangeInfo(displayedProgress, 0f..1f)
                setProgress { seek(it); true }
            }
            .onGloballyPositioned { thumbCoordinates = it }
            .pointerInput(geometry, travel) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val thumbAtDown = thumbCoordinates
                    if (thumbAtDown?.isAttached != true) return@awaitEachGesture
                    val startY = thumbAtDown.localToRoot(down.position).y
                    val startProgress = latestProgress
                    val startTop = latestTop.toFloat()
                    down.consume()
                    dragProgress = latestProgress
                    dragging = true
                    try {
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull { it.id == down.id } ?: break
                            // Read live layout coordinates: cached positions can lag a moving handle by a frame.
                            val thumb = thumbCoordinates
                            if (thumb?.isAttached == true) {
                                val position = thumb.localToRoot(change.position).y
                                val delta = position - startY
                                // Preserve both endpoints when the thumb was moved clear of a header.
                                latestSeek(if (delta >= 0f)
                                    startProgress + delta / (travel - startTop).coerceAtLeast(1f) * (1f - startProgress)
                                else startProgress + delta / startTop.coerceAtLeast(1f) * startProgress)
                            }
                            change.consume()
                            if (!change.pressed) break
                        }
                    } finally {
                        dragging = false
                    }
                }
            }, contentAlignment = Alignment.CenterEnd) {
            Box(Modifier.padding(end = 4.dp).width(thumbWidth).height(36.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = if (dragging) 1f else 0.65f), CircleShape))
        }
    }
}
