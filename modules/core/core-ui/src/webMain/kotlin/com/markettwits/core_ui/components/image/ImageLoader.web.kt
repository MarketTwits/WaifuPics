package com.markettwits.core_ui.components.image

import coil3.ImageLoader
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger

actual fun coil3.PlatformContext.asyncImageLoader(): coil3.ImageLoader{
    return coil3.ImageLoader
        .Builder(this)
        .components {
            add(KtorNetworkFetcherFactory())
            add(ProxyInterceptor())
        }
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
}