package com.markettwits.waifupics.gallery.item.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.gallery.common.rememberGalleryImageRequest
import com.markettwits.waifupics.gallery.common.GalleryDeletion
import org.koin.compose.koinInject
import com.markettwits.core_ui.components.zoomable.rememberZoomState
import com.markettwits.core_ui.components.zoomable.ZoomState
import androidx.compose.ui.geometry.Offset
import com.markettwits.waifupics.gallery.item.components.bottomBar.BottomBarScreenImage
import com.markettwits.waifupics.gallery.item.components.image.ZoomablePagerImage
import com.markettwits.waifupics.gallery.item.components.image.GalleryImageBackdrop
import com.markettwits.waifupics.gallery.item.components.image.PrefetchGalleryBackdrops
import com.markettwits.waifupics.gallery.item.components.topbar.TopBarScreenImage
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun ImageScreenFull(modifier: Modifier = Modifier,
    imageTransition: GalleryImageTransition? = null,
    onBottomOverlayHeightChanged: (Dp) -> Unit = {},
    onCurrentImageChanged: (Int) -> Unit = {}, onClickPop: () -> Unit) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()
    val deletion = koinInject<GalleryDeletion>()
    val currentImage by viewModel.currentImage().collectAsState()
    val state by viewModel.state().collectAsState()
    val images = remember(state.items) { state.items.filterIsInstance<ImageFavoriteUi.Base>() }
    val ids = remember(images) { images.map { it.id } }
    val pagerState = rememberPagerState(initialPage = images.indexOfFirst { it.id == currentImage.id }.coerceAtLeast(0)) {
        images.size
    }
    var visibleId by rememberSaveable { mutableIntStateOf(currentImage.id) }
    var restoredFocus by remember { mutableStateOf<Int?>(null) }
    var showUI by rememberSaveable { mutableStateOf(true) }
    var previewIndex by remember { mutableStateOf<Int?>(null) }
    var previewCommitted by remember { mutableStateOf(false) }
    var navigatingBack by remember { mutableStateOf(false) }
    var activeZoom by remember { mutableStateOf<ZoomState?>(null) }
    val scope = rememberCoroutineScope()
    var navigationJob by remember { mutableStateOf<Job?>(null) }
    val dismissOffset = remember { Animatable(0f) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var dragActive by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val dismissThreshold = with(density) { 120.dp.toPx() }
    val reportImage by rememberUpdatedState(onCurrentImageChanged)
    val pop by rememberUpdatedState(onClickPop)
    val close: () -> Unit = {
        if (!navigatingBack) {
            navigatingBack = true
            navigationJob?.cancel()
            scope.launch {
                val zoom = activeZoom
                if (zoom != null && zoom.scale > 1.01f) zoom.changeScale(1f, Offset.Zero, tween(160))
                pop()
            }
        }
    }
    // NavHost owns predictive back: it seeks the shared image transition with the gesture,
    // animates cancellation back to the viewer, and pops only after a committed gesture.
    LaunchedEffect(imageTransition?.visible) {
        if (imageTransition?.visible == false) {
            navigationJob?.cancel()
            previewIndex = null
            previewCommitted = false
            // Keep one stable source image while Navigation seeks the return transition.
            if (images.isNotEmpty() && pagerState.isScrollInProgress) {
                pagerState.scrollToPage(pagerState.currentPage.coerceIn(images.indices))
            }
        }
    }
    LaunchedEffect(deletion) { deletion.restoredFocus.collect {
        if (!navigatingBack && imageTransition?.visible != false) restoredFocus = it
    } }
    LaunchedEffect(ids, restoredFocus) {
        if (images.isEmpty()) {
            close()
            return@LaunchedEffect
        }
        val restoredIndex = images.indexOfFirst { it.id == restoredFocus }.takeIf { it >= 0 }
        if (restoredIndex != null) {
            visibleId = images[restoredIndex].id
        }
        val index = restoredIndex ?: images.indexOfFirst { it.id == visibleId }.takeIf { it >= 0 }
            ?: pagerState.currentPage.coerceIn(images.indices)
        pagerState.scrollToPage(index)
        if (restoredIndex != null) restoredFocus = null
        snapshotFlow { pagerState.currentPage }.collect { page ->
            images.getOrNull(page)?.let {
                visibleId = it.id
                viewModel.setCurrentItem(page)
                reportImage(it.id)
                if (previewCommitted && previewIndex == page) {
                    previewIndex = null
                    previewCommitted = false
                }
            }
        }
    }
    if (images.isEmpty()) return
    PrefetchGalleryBackdrops(images, pagerState)
    val page = pagerState.currentPage.coerceIn(images.indices)
    val displayedIndex = previewIndex?.coerceIn(images.indices) ?: page
    val displayed = images[displayedIndex]
    val busy = imageTransition?.running == true || navigatingBack
    val controlsVisible = showUI && dragOffset == 0f && !busy
    val imageOffset = if (dragActive) dragOffset else dismissOffset.value
    val dismissProgress = (imageOffset / (dismissThreshold * 3)).coerceIn(0f, 1f)
    Box(modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize(), pageSpacing = 12.dp,
            beyondViewportPageCount = 1, userScrollEnabled = !busy && previewIndex == null,
            flingBehavior = PagerDefaults.flingBehavior(pagerState), key = { images[it].id }) { index ->
            val image = images[index]
            val zoom = rememberZoomState(maxScale = 8f)
            val active = index == page
            DisposableEffect(active, zoom) {
                if (active) activeZoom = zoom
                onDispose { if (activeZoom === zoom) activeZoom = null }
            }
            val canDismiss = active && !busy && previewIndex == null && zoom.scale <= 1.01f
            Box(Modifier.fillMaxSize()
                .pointerInput(canDismiss) {
                    if (!canDismiss) return@pointerInput
                    var dismissing = false
                    detectVerticalDragGestures(
                        onDragStart = {
                            dismissing = zoom.scale <= 1.01f
                            if (dismissing) {
                                scope.launch { dismissOffset.stop() }
                                dragOffset = dismissOffset.value
                                dragActive = true
                            }
                        },
                        onVerticalDrag = { change, delta ->
                            if (dismissing) {
                                change.consume()
                                dragOffset = (dragOffset + delta).coerceAtLeast(0f)
                            }
                        },
                        onDragEnd = {
                            if (dismissing) {
                                if (dragOffset >= dismissThreshold) {
                                    scope.launch { dismissOffset.snapTo(dragOffset); dragActive = false; close() }
                                }
                                else scope.launch {
                                    dismissOffset.snapTo(dragOffset)
                                    dragActive = false
                                    dismissOffset.animateTo(0f, spring())
                                    dragOffset = 0f
                                }
                            }
                        },
                        onDragCancel = {
                            if (dismissing) scope.launch {
                                dismissOffset.snapTo(dragOffset); dragActive = false
                                dismissOffset.animateTo(0f, spring()); dragOffset = 0f
                            }
                        }
                    )
                }.offset { IntOffset(0, if (active) imageOffset.roundToInt() else 0) }.graphicsLayer {
                    if (active) {
                        alpha = 1f - dismissProgress * 0.3f
                    }
                }) {
                ZoomablePagerImage(image, zoom, showUI, active,
                    resetZoom = !active && !pagerState.isScrollInProgress,
                    frameScale = if (active) 1f - dismissProgress * 0.16f else 1f,
                    imageTransition = imageTransition) {
                    showUI = !showUI
                }
            }
        }
        if (previewIndex != null) {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                GalleryImageBackdrop(displayed, showUI)
                AsyncImage(model = rememberGalleryImageRequest(displayed), contentDescription = null,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
            }
        }
        TopBarScreenImage(showUI = controlsVisible, createdDate = displayed.created,
            position = displayedIndex + 1, total = images.size,
            informationEnabled = previewIndex == null && !pagerState.isScrollInProgress && currentImage.id == displayed.id,
            onClickGoBack = close)
        BottomBarScreenImage(showUI = controlsVisible, images = images, selectedIndex = displayedIndex,
            onPreview = {
                navigationJob?.cancel()
                previewCommitted = false
                previewIndex = it
            }, onSelect = { target, animate ->
                navigationJob?.cancel()
                if (target == page) { previewIndex = null; previewCommitted = false }
                else {
                    val animatePage = animate && abs(target - page) <= 1
                    if (!animatePage) { previewIndex = target; previewCommitted = true }
                    navigationJob = scope.launch {
                        if (animatePage) pagerState.animateScrollToPage(target, animationSpec = tween(240))
                        else pagerState.scrollToPage(target)
                        previewIndex = null
                        previewCommitted = false
                    }
                }
            }, actionsEnabled = previewIndex == null && !pagerState.isScrollInProgress && currentImage.id == displayed.id,
            onClickDeleteImage = viewModel::onClickDelete, onClickShareImage = viewModel::onClickShareImage,
            onHeightChanged = onBottomOverlayHeightChanged)
    }
}
