package com.markettwits.waifupics.view.main.data

import android.content.Context
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest

interface ImageLoader {
    fun imageRequestInner(url: String) : ImageRequest
    suspend fun load(url: String)
    class Base(private val context: Context) : ImageLoader{
        override fun imageRequestInner(url : String) =
                ImageRequest.Builder(context)
                    .data(url)
                    .crossfade(true)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey("mt05s")
                    .build()
        override suspend fun load(url : String){
            context.imageLoader.enqueue(request = imageRequestInner(url))
        }
    }
}