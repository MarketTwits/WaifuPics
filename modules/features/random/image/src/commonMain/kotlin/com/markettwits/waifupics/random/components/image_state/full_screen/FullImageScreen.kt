package com.markettwits.waifupics.random.components.image_state.full_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.ZoomOutMap
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.markettwits.core_ui.components.design.WaifuOverlayButton
import com.markettwits.core_ui.components.zoomable.ScrollGesturePropagation
import com.markettwits.core_ui.components.zoomable.rememberZoomState
import com.markettwits.core_ui.components.zoomable.zoomable
import com.markettwits.waifupics.random.components.image_state.suceess.AnimatedImageBackground
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun FullImageScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
    preview: Painter,
    colorPalette: List<List<Int>>,
    imageTransition: HomeImageTransition,
    transitionRunning: Boolean,
    isVisible: Boolean,
    onDismiss: () -> Unit
) {
    val zoomState = rememberZoomState(maxScale = 25f)
    val context = LocalPlatformContext.current
    val imageRequest = remember(context, imageUrl) {
        ImageRequest.Builder(context).data(imageUrl).size(coil3.size.Size.ORIGINAL).build()
    }
    var retryAttempt by remember { mutableIntStateOf(0) }
    val fullPainter = key(retryAttempt) { rememberAsyncImagePainter(imageRequest) }
    val fullState by fullPainter.state.collectAsState()
    // Keep the already decoded artwork throughout the shared transition, even on a slow connection.
    val painter = if (!transitionRunning && fullState is AsyncImagePainter.State.Success) fullPainter else preview
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var returnJob by remember { mutableStateOf<Job?>(null) }
    var showControls by remember { mutableStateOf(true) }
    var closing by remember { mutableStateOf(false) }
    val dismiss by rememberUpdatedState(onDismiss)
    val isZoomed = zoomState.scale > 1.01f
    val density = LocalDensity.current
    val dismissThreshold = with(density) { 120.dp.toPx() }
    val flingMinimumDistance = with(density) { 32.dp.toPx() }
    val dismissVelocity = with(density) { 1200.dp.toPx() }
    val closeDescription = stringResource(Res.string.close_full_image)
    val resetDescription = stringResource(Res.string.reset_image_zoom)
    val zoomDescription = stringResource(if (isZoomed) Res.string.zoomed_image else Res.string.original_image_size)
    val close: () -> Unit = {
        if (isVisible && !closing) {
            closing = true
            returnJob?.cancel()
            scope.launch {
                if (isZoomed) zoomState.changeScale(1f, Offset.Zero, tween(160))
                dismiss()
            }
        }
    }
    val canDismiss = isVisible && !closing && !transitionRunning && !isZoomed
    val controlsVisible = showControls && isVisible && !closing && !transitionRunning && abs(dragOffset) < 1f
    // Consume repeated Back presses until the return transition has disposed the viewer.
    BackHandler(enabled = true, onBack = close)
    LaunchedEffect(isVisible) { if (isVisible) focusRequester.requestFocus() }
    LaunchedEffect(painter) {
        val success = fullState as? AsyncImagePainter.State.Success
        zoomState.setContentSize(if (painter === fullPainter && success != null)
            Size(success.result.image.width.toFloat(), success.result.image.height.toFloat()) else preview.intrinsicSize)
    }
    LaunchedEffect(isZoomed) {
        if (isZoomed) { returnJob?.cancel(); dragOffset = 0f }
    }
    fun returnToCenter() {
        returnJob?.cancel()
        returnJob = scope.launch {
            animate(dragOffset, 0f, animationSpec = spring(dampingRatio = 0.9f, stiffness = 450f)) { value, _ ->
                dragOffset = value
            }
        }
    }

    BoxWithConstraints(modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).clipToBounds()
        .focusRequester(focusRequester).onPreviewKeyEvent {
            if (it.key == Key.Escape && it.type == KeyEventType.KeyUp) { close(); true } else false
        }.focusable().semantics {
            stateDescription = zoomDescription
            customActions = listOf(CustomAccessibilityAction(closeDescription) { close(); true },
                CustomAccessibilityAction(resetDescription) {
                    scope.launch { zoomState.changeScale(1f, Offset.Zero, tween(200)) }; true
                })
        }, contentAlignment = Alignment.Center) {
        val heightPx = with(density) { maxHeight.toPx() }.coerceAtLeast(1f)
        val dragProgress = (abs(dragOffset) / (heightPx * 0.45f)).coerceIn(0f, 1f)
        Box(Modifier.fillMaxSize().graphicsLayer { alpha = 1f - dragProgress * 0.7f }) {
            AnimatedImageBackground(preview, colorPalette,
                animate = isVisible && !transitionRunning && !closing && dragOffset == 0f)
        }
        // The picture stays fitted inside system insets and the header; the backdrop remains edge to edge.
        Box(Modifier.fillMaxSize().safeDrawingPadding().padding(top = 56.dp, bottom = 12.dp)) {
            Box(Modifier.fillMaxSize().pointerInput(canDismiss) {
                if (!canDismiss) return@pointerInput
                val velocity = VelocityTracker()
                detectVerticalDragGestures(
                    onDragStart = {
                        returnJob?.cancel()
                        velocity.resetTracking()
                    },
                    onVerticalDrag = { change, delta ->
                        change.consume()
                        velocity.addPosition(change.uptimeMillis, change.position)
                        dragOffset += delta
                    },
                    onDragEnd = {
                        if (shouldDismissHomeImage(dragOffset, velocity.calculateVelocity().y,
                                dismissThreshold, flingMinimumDistance, dismissVelocity)) close()
                        else returnToCenter()
                    },
                    onDragCancel = { if (!closing) returnToCenter() }
                )
            }.offset { IntOffset(0, dragOffset.roundToInt()) }.graphicsLayer {
                scaleX = 1f - dragProgress * 0.12f
                scaleY = 1f - dragProgress * 0.12f
            }) {
                HomeSharedImage(painter, stringResource(Res.string.full_image), imageTransition,
                    Modifier.clipToBounds().zoomable(zoomState,
                        zoomEnabled = isVisible && !closing && !transitionRunning,
                        scrollGesturePropagation = ScrollGesturePropagation.NotZoomed,
                        onTap = { if (isVisible && !closing && !transitionRunning) showControls = !showControls }))
            }
        }
        AnimatedVisibility(controlsVisible,
            modifier = Modifier.align(Alignment.TopCenter).safeDrawingPadding(),
            enter = fadeIn(tween(180)) + slideInVertically(tween(220)) { -it / 4 },
            exit = fadeOut(tween(120)) + slideOutVertically(tween(160)) { -it / 4 }) {
            Row(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                FullImageButton(closeDescription, close) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(22.dp))
                }
                Spacer(Modifier.weight(1f))
                if (fullState is AsyncImagePainter.State.Error) FullImageButton(
                    stringResource(Res.string.retry_loading), { retryAttempt++ }) {
                    Icon(Icons.Outlined.Refresh, null, Modifier.size(22.dp))
                }
                AnimatedVisibility(isZoomed, enter = fadeIn(tween(160)), exit = fadeOut(tween(120))) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                            contentColor = MaterialTheme.colorScheme.secondary) {
                            val scaleLabel = (zoomState.scale * 10).roundToInt()
                            Text("${scaleLabel / 10}.${scaleLabel % 10}×", Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium)
                        }
                        FullImageButton(resetDescription, { scope.launch { zoomState.changeScale(1f, Offset.Zero, tween(200)) } }) {
                            Icon(Icons.Outlined.ZoomOutMap, null, Modifier.size(22.dp))
                        }
                    }
                }
            }
        }
    }
}

internal fun shouldDismissHomeImage(offset: Float, velocity: Float, threshold: Float,
    minimumFlingDistance: Float, minimumVelocity: Float): Boolean =
    abs(offset) >= threshold || (abs(offset) >= minimumFlingDistance && abs(velocity) >= minimumVelocity && offset * velocity > 0)

@Composable
private fun FullImageButton(description: String, onClick: () -> Unit, content: @Composable () -> Unit) {
    WaifuOverlayButton(description, onClick, content = content)
}
