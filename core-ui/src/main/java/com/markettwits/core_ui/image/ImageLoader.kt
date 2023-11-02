package com.markettwits.core_ui.image

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import coil.request.CachePolicy
import coil.request.ImageRequest

interface ImageLoader {
    fun memorySingle(data: Any) : ImageRequest
    fun memoryWithDisk(data: Any) : ImageRequest
    class Base(private val context: Context) : ImageLoader{
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