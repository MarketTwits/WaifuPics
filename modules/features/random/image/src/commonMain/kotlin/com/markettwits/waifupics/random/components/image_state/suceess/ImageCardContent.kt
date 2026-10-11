package com.markettwits.waifupics.random.components.image_state.suceess

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ImageCardContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    id: Int,
    colorPalette: List<List<Int>>,
    animateBackground: Boolean,
    sharedKey: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onChangeImageState: (ImageState) -> Unit,
    onDoubleTapFavorite: () -> Boolean,
    onOpenFullScreen: () -> Unit
) {

    val boundsState = rememberSharedContentState(key = "${sharedKey}_bounds")
    val elementState = rememberSharedContentState(key = sharedKey)
    var retryAttempt by remember(id, imageUrl) { mutableIntStateOf(0) }
    val hearts = remember(id, imageUrl) { mutableStateListOf<FavoriteHeart>() }
    var heartSequence by remember(id, imageUrl) { mutableIntStateOf(0) }
    val openFullScreen by rememberUpdatedState(onOpenFullScreen)
    val favorite by rememberUpdatedState(onDoubleTapFavorite)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = boundsState,
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ -> spring(dampingRatio = 0.75f, stiffness = 400f) }
            ),
        contentAlignment = Alignment.Center
    ) {
        key(retryAttempt) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = modifier
                        .fillMaxSize()
                        .testTag("feed-image-$id")
                        .sharedElement(
                            sharedContentState = elementState,
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ -> spring(dampingRatio = 0.75f, stiffness = 400f) }
                        ),
                    model = imageUrl,
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    contentDescription = "View image",
                    loading = {
                        ImageLoading()
                        LaunchedEffect(id, imageUrl) { onChangeImageState(ImageState.Loading) }
                    }, success = {
                        val imageScope = this
                        Box(Modifier.fillMaxSize()
                            .semantics { onClick { openFullScreen(); true } }
                            .pointerInput(id, imageUrl) {
                                detectTapGestures(
                                    onTap = { openFullScreen() },
                                    onDoubleTap = { position ->
                                        if (favorite()) {
                                            if (hearts.size >= 5) hearts.removeAt(0)
                                            hearts.add(FavoriteHeart(++heartSequence, position))
                                        }
                                    }
                                )
                            }, contentAlignment = Alignment.Center) {
                            AnimatedImageBackground(it.painter, colorPalette, animateBackground)
                            imageScope.SubcomposeAsyncImageContent(
                                modifier = Modifier.fillMaxSize().softFitEdges(it.painter.intrinsicSize),
                                contentScale = ContentScale.Fit,
                                alignment = Alignment.Center
                            )
                            FavoriteHeartOverlay(hearts, onFinished = { hearts.remove(it) })
                        }
                        LaunchedEffect(id, imageUrl, it.result) {
                            onChangeImageState(
                                ImageState.Success(
                                    id,
                                    imageUrl,
                                    it.result.image.width,
                                    it.result.image.height,
                                    false
                                )
                            )
                        }
                    },
                    error = {
                        val message = it.result.throwable.message ?: "Не удалось загрузить изображение"
                        ImageFuckup(onRetry = { retryAttempt++ })
                        LaunchedEffect(id, imageUrl, it.result) {
                            onChangeImageState(ImageState.Error(message))
                        }
                    }
                )
            }
        }
    }
}

internal data class FavoriteHeart(val sequence: Int, val position: Offset)
