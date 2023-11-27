package com.markettwits.core_ui.image

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import coil.request.CachePolicy
import coil.request.ImageRequest

interface ImageLoader {

    fun single(data: Any) : ImageRequest
    fun memorySingle(data: Any) : ImageRequest
    fun memoryWithDisk(data: Any) : ImageRequest
    class Base(private val context: Context) : ImageLoader{
        override fun single(data: Any) = ImageRequest.Builder(context)
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