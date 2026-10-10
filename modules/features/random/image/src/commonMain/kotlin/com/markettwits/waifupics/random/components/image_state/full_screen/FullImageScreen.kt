package com.markettwits.waifupics.random.components.image_state.full_screen

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material3.Icon
import com.markettwits.core_ui.components.design.WaifuOverlayButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.markettwits.core_ui.components.zoomable.ScrollGesturePropagation
import com.markettwits.core_ui.components.zoomable.rememberZoomState
import com.markettwits.core_ui.components.zoomable.zoomable
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*

@Composable
internal fun FullImageScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isVisible: Boolean,
    transformOrigin: TransformOrigin,
    onDismiss: () -> Unit
) {
    val zoomState = rememberZoomState(maxScale = 25f)
    val context = LocalPlatformContext.current
    val imageRequest = remember(context, imageUrl) {
        // The original-size zoom stage needs the full image, not a viewport-sized decode.
        ImageRequest.Builder(context).data(imageUrl).size(coil3.size.Size.ORIGINAL).build()
    }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var returnJob by remember { mutableStateOf<Job?>(null) }
    var retryAttempt by remember(imageUrl) { mutableIntStateOf(0) }
    var imageReady by remember(imageUrl) { mutableStateOf(false) }
    val isZoomed = zoomState.scale > 1.01f
    val density = LocalDensity.current
    val dismissThreshold = with(density) { 120.dp.toPx() }
    val flingMinimumDistance = with(density) { 32.dp.toPx() }
    val dismissVelocity = with(density) { 1200.dp.toPx() }
    val zoomDescription = stringResource(if (isZoomed) Res.string.zoomed_image else Res.string.original_image_size)

    LaunchedEffect(imageUrl) { zoomState.reset() }

    LaunchedEffect(isVisible) {
        if (isVisible) focusRequester.requestFocus()
    }
    LaunchedEffect(isZoomed) {
        if (isZoomed) {
            returnJob?.cancel()
            dragOffset = 0f
        }
    }

    BoxWithConstraints(
        modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).clipToBounds()
            .focusRequester(focusRequester)
            .onPreviewKeyEvent {
                if (it.key == Key.Escape && it.type == KeyEventType.KeyUp) {
                    onDismiss()
                    true
                } else false
            }.focusable(),
        contentAlignment = Alignment.Center
    ) {
        val heightPx = with(density) { maxHeight.toPx() }.coerceAtLeast(1f)
        val dragProgress = (abs(dragOffset) / heightPx).coerceIn(0f, 1f)
        Image(rememberAsyncImagePainter(imageUrl), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.3f * (1f - dragProgress)).blur(32.dp))
        key(retryAttempt) {
            SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = stringResource(Res.string.full_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
                    .semantics { stateDescription = zoomDescription }
                    .graphicsLayer {
                        this.transformOrigin = transformOrigin
                        translationY = dragOffset
                        scaleX = 1f - 0.12f * dragProgress
                        scaleY = 1f - 0.12f * dragProgress
                    }
                    .draggable(
                        state = rememberDraggableState { delta -> dragOffset += delta },
                        enabled = isVisible && !isZoomed,
                        orientation = Orientation.Vertical,
                        onDragStarted = { returnJob?.cancel() },
                        onDragStopped = { velocity ->
                            if (abs(dragOffset) >= dismissThreshold ||
                                (abs(dragOffset) >= flingMinimumDistance && abs(velocity) >= dismissVelocity)) {
                                onDismiss()
                            } else {
                                returnJob = scope.launch {
                                    animate(dragOffset, 0f, animationSpec = spring(stiffness = 500f)) { value, _ ->
                                        dragOffset = value
                                    }
                                }
                            }
                        }
                    )
                    .zoomable(
                        zoomState = zoomState,
                        zoomEnabled = imageReady && isVisible,
                        scrollGesturePropagation = ScrollGesturePropagation.NotZoomed
                    ),
                loading = {
                    ImageLoading()
                    LaunchedEffect(Unit) { imageReady = false }
                },
                error = {
                    ImageFuckup(onRetry = { retryAttempt++ })
                    LaunchedEffect(Unit) { imageReady = false }
                },
                success = {
                    SubcomposeAsyncImageContent(contentScale = ContentScale.Fit)
                    LaunchedEffect(it.painter) {
                        zoomState.setContentSize(Size(it.result.image.width.toFloat(), it.result.image.height.toFloat()))
                        imageReady = true
                    }
                }
            )
        }
        Row(
            modifier = Modifier.align(Alignment.TopEnd).safeDrawingPadding().padding(top = 8.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (isZoomed) FullImageButton(
                description = stringResource(Res.string.reset_image_zoom),
                onClick = { scope.launch { zoomState.changeScale(1f, Offset.Zero, tween(180)) } }
            ) {
                Icon(Icons.Default.ZoomOutMap, null, Modifier.size(22.dp))
            }
            FullImageButton(description = stringResource(Res.string.close_full_image), onClick = onDismiss) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(22.dp))
            }
        }
    }
}

@Composable
private fun FullImageButton(description: String, onClick: () -> Unit, content: @Composable () -> Unit) {
    WaifuOverlayButton(description, onClick, tint = MaterialTheme.colorScheme.onSurface, content = content)
}
