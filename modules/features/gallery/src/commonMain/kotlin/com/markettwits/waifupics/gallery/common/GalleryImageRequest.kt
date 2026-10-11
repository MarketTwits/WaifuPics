package com.markettwits.waifupics.gallery.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi

/** Reuse the loaded grid image while the viewer decodes its full-resolution replacement. */
@Composable
internal fun rememberGalleryImageRequest(image: ImageFavoriteUi): ImageRequest {
    val context = LocalPlatformContext.current
    return remember(context, image.id, image.imageUrl) {
        val key = galleryImageCacheKey(image)
        ImageRequest.Builder(context).data(image.imageUrl)
            .memoryCacheKey(key).placeholderMemoryCacheKey(key).crossfade(false).build()
    }
}

internal fun galleryImageCacheKey(image: ImageFavoriteUi): String = "gallery-${image.id}-${image.imageUrl}"
