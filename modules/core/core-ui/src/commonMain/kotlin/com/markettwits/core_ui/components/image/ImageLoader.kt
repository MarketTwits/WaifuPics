package com.markettwits.core_ui.components.image

import coil3.ComponentRegistry
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.intercept.Interceptor
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.ImageResult
import coil3.request.crossfade
import coil3.util.DebugLogger


expect fun PlatformContext.asyncImageLoader(): ImageLoader

fun PlatformContext.DefultAsyncImageLoader(): coil3.ImageLoader {
    return coil3.ImageLoader
        .Builder(this)
        .components {
            add(KtorNetworkFetcherFactory())
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

internal class ProxyInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request
        val data = request.data

        if (data is String && (data.startsWith("http://") || data.startsWith("https://"))) {
            val proxiedUrl = "$PROXY_IMAGE_BASE_URL/?$PROXY_IMAGE_BASE_PATH=$data"
            val newRequest = chain.request.newBuilder()
                .data(proxiedUrl)
                .build()

            return chain.withRequest(newRequest).proceed()
        }
        return chain.proceed()
    }

    private companion object{
        val PROXY_IMAGE_BASE_URL = "https://wsrv.nl"
        val PROXY_IMAGE_BASE_PATH = "url"
    }
}