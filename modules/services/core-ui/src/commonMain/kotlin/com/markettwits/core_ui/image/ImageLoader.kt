package com.markettwits.core_ui.image

import androidx.compose.runtime.compositionLocalOf
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest


interface ImageLoader {

    fun single(data: Any) : ImageRequest

    fun memorySingle(data: Any) : ImageRequest

    fun memoryWithDisk(data: Any) : ImageRequest

    class Base(private val context: PlatformContext) : ImageLoader{
        override fun single(data: Any) =  ImageRequest.Builder(context)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .data(data = data)
            .build()

        override fun memorySingle(data: Any) = ImageRequest.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .data(data = data)
            .build()
        override fun memoryWithDisk(data: Any) = ImageRequest.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .data(data = data)
            .build()
    }
}

val LocalImageLoader = compositionLocalOf<ImageLoader> { error("Image loader is not available") }