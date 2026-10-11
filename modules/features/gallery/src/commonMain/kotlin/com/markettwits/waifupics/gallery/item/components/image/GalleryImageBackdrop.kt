package com.markettwits.waifupics.gallery.item.components.image

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.asPainter
import coil3.compose.rememberAsyncImagePainter
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import coil3.size.Scale
import com.markettwits.waifupics.gallery.common.galleryImageCacheKey
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/** Independent of the original-size decode; the grid cache is available on the first draw. */
@Composable
internal fun GalleryImageBackdrop(image: ImageFavoriteUi, uiEnabled: Boolean, fallback: Painter? = null) {
    val context = LocalPlatformContext.current
    val loader = remember(context) { SingletonImageLoader.get(context) }
    val cachedPainter = remember(loader, image.id, image.imageUrl) {
        (loader.memoryCache?.get(MemoryCache.Key(backdropCacheKey(image)))
            ?: loader.memoryCache?.get(MemoryCache.Key(galleryImageCacheKey(image))))?.image?.asPainter(context)
    }
    val request = remember(context, image.id, image.imageUrl) { backdropRequest(context, image) }
    val painter = rememberAsyncImagePainter(request, error = cachedPainter, contentScale = ContentScale.Crop)
    val state by painter.state.collectAsState()
    // AsyncImagePainter can still be Empty in the first frame, even on a memory-cache hit.
    val displayed = if (state is AsyncImagePainter.State.Success) painter
        else cachedPainter ?: state.painter ?: fallback ?: painter
    val opacity by animateFloatAsState(if (uiEnabled) 0.45f else 0.2f, label = "Viewer backdrop opacity")
    BoxWithConstraints(Modifier.fillMaxSize().clipToBounds(), contentAlignment = Alignment.Center) {
        // One sixteenth of the old blur buffer; 12 dp becomes 48 dp after enlargement.
        Image(displayed, null, contentScale = ContentScale.Crop,
            modifier = Modifier.size(maxWidth * 0.25f, maxHeight * 0.25f).graphicsLayer {
                scaleX = 4f
                scaleY = 4f
                alpha = opacity
            }.blur(12.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded))
    }
}

/** The pager already prepares neighbours; warm the next two pages in either direction. */
@Composable
internal fun PrefetchGalleryBackdrops(images: List<ImageFavoriteUi>, pager: PagerState) {
    val context = LocalPlatformContext.current
    val loader = remember(context) { SingletonImageLoader.get(context) }
    LaunchedEffect(loader, images, pager) {
        snapshotFlow { pager.currentPage }.distinctUntilChanged().collectLatest { current ->
            coroutineScope {
                listOf(-3, -2, 2, 3).forEach { offset ->
                    val image = images.getOrNull(current + offset) ?: return@forEach
                    if (loader.memoryCache?.get(MemoryCache.Key(backdropCacheKey(image))) == null) {
                        launch { loader.execute(backdropRequest(context, image)) }
                    }
                }
            }
        }
    }
}

private fun backdropCacheKey(image: ImageFavoriteUi): String = "${galleryImageCacheKey(image)}-backdrop"

private fun backdropRequest(context: PlatformContext, image: ImageFavoriteUi): ImageRequest =
    ImageRequest.Builder(context).data(image.imageUrl).size(256).scale(Scale.FIT)
        .precision(Precision.INEXACT).memoryCacheKey(backdropCacheKey(image))
        .placeholderMemoryCacheKey(galleryImageCacheKey(image)).crossfade(false).build()
