package com.markettwits.waifupics.random.components

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.filter.components.AgeRatingFilter
import com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel
import com.markettwits.waifupics.random.components.bottom_info.BottomImageInfo
import com.markettwits.waifupics.random.components.bottom_info.ImageInformationSheet
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading
import com.markettwits.waifupics.random.components.image_state.suceess.ImageCardContent
import com.markettwits.waifupics.random.components.image_state.full_screen.FullImageScreen
import com.markettwits.waifupics.random.components.image_state.full_screen.HomeImageTransition
import com.markettwits.waifupics.random.components.vertical_panel.VerticalControlPanel
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.viewmodel.ImageViewModel
import kotlin.math.max
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.retry_loading
import waifupics.modules.features.random.image.generated.resources.feed_load_failed

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RandomImageScreen(
    randomImageViewModel: ImageViewModel = ApplicationViewModel(),
    filterViewModel: AgeRatingFilterViewModel = ApplicationViewModel(),
    paddingValues: PaddingValues,
    onViewerVisibilityChanged: (Boolean) -> Unit = {},
) {
    val state by randomImageViewModel.state().collectAsState()
    val images by randomImageViewModel.images().collectAsState()
    val currentIndex by randomImageViewModel.currentIndex().collectAsState()
    val requestedPage by randomImageViewModel.requestedPage().collectAsState()
    val isFetching by randomImageViewModel.isFetching().collectAsState()
    val loadingError by randomImageViewModel.loadingError().collectAsState()
    val imageState by randomImageViewModel.loadedImageState().collectAsState()
    val favorites by randomImageViewModel.favorites().collectAsState()
    var fullscreen by remember { mutableStateOf<FullscreenData?>(null) }
    var information by remember { mutableStateOf<ImageDetails?>(null) }
    val fullscreenTransition = updateTransition(fullscreen?.isVisible == true, label = "Home image viewer")
    val snackbar = remember { SnackbarHostState() }
    val pagerState = rememberPagerState(initialPage = currentIndex, pageCount = { max(images.size, 1) })
    val hasImages = images.isNotEmpty()
    val retryLabel = stringResource(Res.string.retry_loading)
    val feedErrorLabel = stringResource(Res.string.feed_load_failed)
    val reportViewer by rememberUpdatedState(onViewerVisibilityChanged)
    LaunchedEffect(fullscreen != null) { reportViewer(fullscreen != null) }
    DisposableEffect(Unit) { onDispose { reportViewer(false) } }

    // Only explicit navigation commands drive the pager; swiping never feeds an animation back.
    LaunchedEffect(requestedPage, hasImages) {
        val request = requestedPage ?: return@LaunchedEffect
        if (!hasImages || request.page !in images.indices) return@LaunchedEffect
        try {
            if (request.page == 0) pagerState.scrollToPage(request.page)
            else pagerState.animateScrollToPage(request.page)
        } catch (e: CancellationException) {
            // A finger can interrupt an animation without cancelling this screen's effect.
            currentCoroutineContext().ensureActive()
            snapshotFlow { pagerState.isScrollInProgress }.first { !it }
        }
        randomImageViewModel.onNavigationCompleted(request, pagerState.settledPage)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow {
            if (images.isNotEmpty() && requestedPage == null && !pagerState.isScrollInProgress)
                pagerState.settledPage else null
        }.collect { page ->
            if (page != null) randomImageViewModel.onPageChanged(page)
        }
    }
    LaunchedEffect(state, loadingError) {
        val error = state as? RandomImageState.Error
        if (error != null && hasImages && snackbar.showSnackbar(if (error.message == loadingError) feedErrorLabel else error.message,
                actionLabel = if (error.message == loadingError) retryLabel else null) == SnackbarResult.ActionPerformed) {
            randomImageViewModel.retryLoading()
        }
    }
    LaunchedEffect(images) {
        if (fullscreen != null && images.none {
                it is RandomImageState.Success && "image_${it.id}" == fullscreen?.key
            }) fullscreen = null
        if (information != null && images.none {
                it is RandomImageState.Success && it.id == information?.image?.id &&
                    it.imageUrl == information?.image?.imageUrl
            }) information = null
    }
    val activeImage = images.getOrNull(currentIndex) as? RandomImageState.Success
    val actionsReady = !pagerState.isScrollInProgress && requestedPage == null

    SharedTransitionLayout {
        LaunchedEffect(fullscreenTransition) {
            snapshotFlow {
                !isTransitionActive && !fullscreenTransition.isRunning &&
                    !fullscreenTransition.currentState && !fullscreenTransition.targetState
            }.collect { closed ->
                if (closed && fullscreen?.isVisible == false) fullscreen = null
            }
        }
        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            Box(
                Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                BoxWithConstraints(
                    Modifier.fillMaxSize()
                ) {
                    val compactControls = maxHeight - paddingValues.calculateTopPadding() -
                        paddingValues.calculateBottomPadding() < 420.dp
                    if (!hasImages) {
                        when (state) {
                            is RandomImageState.Error -> ImageFuckup(randomImageViewModel::retryLoading)
                            else -> ImageLoading()
                        }
                    } else {
                        VerticalPager(
                            state = pagerState,
                            key = { page -> (images.getOrNull(page) as? RandomImageState.Success)?.id ?: page },
                            modifier = Modifier.fillMaxSize(),
                            userScrollEnabled = fullscreen == null && information == null,
                            pageSpacing = 0.dp,
                            beyondViewportPageCount = 1
                        ) { page ->
                            val image = images.getOrNull(page) as? RandomImageState.Success
                            if (image != null) {
                                with(this@SharedTransitionLayout) {
                                    ImageCardContent(
                                        imageUrl = image.imageUrl,
                                        id = image.id,
                                        colorPalette = image.colorPalette,
                                        animateBackground = page == pagerState.currentPage &&
                                            !pagerState.isScrollInProgress && fullscreen == null && information == null,
                                        sharedKey = "image_${image.id}",
                                        fullscreenTransition = fullscreenTransition,
                                        isFullscreenImage = fullscreen?.key == "image_${image.id}",
                                        onChangeImageState = {
                                            randomImageViewModel.obtainImageState(image.id, image.imageUrl, it)
                                        },
                                        onDoubleTapFavorite = {
                                            fullscreen == null && actionsReady && page == pagerState.settledPage &&
                                                randomImageViewModel.onDoubleTapFavorite(image.id)
                                        },
                                        onOpenFullScreen = { painter ->
                                            if (fullscreen == null && actionsReady && page == pagerState.settledPage)
                                                fullscreen = FullscreenData(image, painter, true)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    fullscreenTransition.AnimatedVisibility(
                        visible = { !it }, modifier = Modifier.align(Alignment.BottomCenter),
                        enter = fadeIn(tween(200)), exit = fadeOut(tween(140))
                    ) {
                        Box(Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(260.dp)
                            .background(Brush.verticalGradient(listOf(Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.94f)))))
                    }
                    Box(Modifier.fillMaxSize().padding(paddingValues)) {
                        fullscreenTransition.AnimatedVisibility(
                            visible = { !it }, modifier = Modifier.fillMaxSize(),
                            enter = fadeIn(tween(200)), exit = fadeOut(tween(140))
                        ) {
                            Box(Modifier.fillMaxSize()) {
                                activeImage?.let { image ->
                                    BottomImageInfo(
                                        modifier = Modifier.align(Alignment.BottomStart)
                                            .fillMaxWidth().padding(start = 20.dp, end = 88.dp, bottom = 20.dp),
                                        imageData = image.imageData,
                                        colorPalette = image.colorPalette,
                                        loadedImage = (imageState as? ImageState.Success)?.takeIf {
                                            it.id == image.id && it.networkUrl == image.imageUrl
                                        },
                                        compact = compactControls,
                                        infoEnabled = actionsReady && fullscreen == null,
                                        onInfo = {
                                            information = ImageDetails(image, (imageState as? ImageState.Success)?.takeIf {
                                                it.id == image.id && it.networkUrl == image.imageUrl
                                            })
                                        }
                                    )
                                }
                                VerticalControlPanel(
                                    modifier = Modifier.align(Alignment.BottomEnd).padding(end = 12.dp, bottom = 16.dp),
                                    imageState = imageState,
                                    actionsReady = actionsReady && fullscreen == null,
                                    showLabels = !compactControls,
                                    isLoadingNext = isFetching && (images.isEmpty() || currentIndex == images.lastIndex),
                                    isFavorite = activeImage?.id in favorites,
                                    imageId = activeImage?.id,
                                    onClickAddToFavorite = randomImageViewModel::onClickAddToFavorite,
                                    onClickFetchRandomImage = randomImageViewModel::fetchRandomImage,
                                    onClickShareImage = randomImageViewModel::onClickShareImage,
                                    onClickFilter = filterViewModel::toggle
                                )
                            }
                        }
                        SnackbarHost(snackbar, Modifier.align(Alignment.TopCenter).padding(top = 68.dp))
                        AgeRatingFilter(viewModel = filterViewModel)
                        information?.let { details ->
                            ImageInformationSheet(details.image, (imageState as? ImageState.Success)?.takeIf {
                                it.id == details.image.id && it.networkUrl == details.image.imageUrl
                            } ?: details.loadedImage, onDismiss = { information = null })
                        }
                    }
                }
            }
            fullscreenTransition.AnimatedVisibility(
                visible = { it },
                modifier = Modifier.fillMaxSize(),
                enter = fadeIn(tween(280)), exit = fadeOut(tween(240))
            ) {
                val data = fullscreen ?: return@AnimatedVisibility
                FullImageScreen(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = data.image.imageUrl,
                    preview = data.painter,
                    colorPalette = data.image.colorPalette,
                    imageTransition = HomeImageTransition(this@SharedTransitionLayout, this, data.key),
                    transitionRunning = isTransitionActive || fullscreenTransition.isRunning,
                    isVisible = data.isVisible,
                    onDismiss = { fullscreen = data.copy(isVisible = false) }
                )
            }
        }
    }
}

private data class FullscreenData(val image: RandomImageState.Success, val painter: Painter, val isVisible: Boolean) {
    val key: String get() = "image_${image.id}"
}
private data class ImageDetails(val image: RandomImageState.Success, val loadedImage: ImageState.Success?)
