package com.markettwits.core_ui.image

import androidx.compose.runtime.compositionLocalOf
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem


interface ImageLoader {

    fun single(data: Any) : ImageRequest

    fun memorySingle(data: Any) : ImageRequest

    fun memoryWithDisk(data: Any) : ImageRequest

    class Base(private val context: PlatformContext) : ImageLoader{
        override fun single(data: Any) =  ImageRequest.Builder(context)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .data(data = data)
            .build()

        override fun memorySingle(data: Any) = ImageRequest.Builder(context)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .data(data = data)
            .build()

        override fun memoryWithDisk(data: Any) = ImageRequest.Builder(context)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .data(data = data)
            .build()
    }
}

fun PlatformContext.asyncImageLoader() =
    coil3.ImageLoader
        .Builder(this)
        .components { add(KtorNetworkFetcherFactory()) }
        .crossfade(true)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(this, 0.25)
                .strongReferencesEnabled(true)
                .build()
        }
        .logger(DebugLogger())
        .build()

/**
 * Enable disk cache for the [ImageLoader].
 */
fun coil3.ImageLoader.enableDiskCache() = this.newBuilder()
    .diskCache {
        DiskCache.Builder()
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
            .build()
    }.build()

fun getAsyncImageLoader(context: PlatformContext)=
    coil3.ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()

val LocalImageLoader = compositionLocalOf<ImageLoader> { error("Image loader is not available") }