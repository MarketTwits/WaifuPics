package com.markettwits.random_image.data

import android.graphics.drawable.Drawable
import android.util.Log
import com.markettwits.data.ImageRepository
import com.markettwits.random_image.ui.RandomImageUiState
import com.markettwits.waifupics.view.main.data.ImageLoader
import com.markettwits.waifupics.view.main.data.net.MakeService

interface RandomImageRepository {
    suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState
    suspend fun addToFavorite(image : Drawable, url : String,protected : Boolean)
    suspend fun preloadImage(url: String)
    class Base(
        private val imageLoader: ImageLoader,
        private val service: MakeService,
        private val mapper: RandomImageUiMapper,
        private val cache : ImageRepository
    ) : RandomImageRepository {
        override suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState {
            return try {
                val request = service.service().randomImage(filters)
                val image = mapper.map(request.items[0])
                image
            } catch (e: Exception) {
                Log.d("mt05", "repository: " + e.message.toString())
                RandomImageUiState.Error(e.message.toString())
            }
        }

        override suspend fun addToFavorite(image: Drawable, url: String, protected: Boolean) {
            cache.addOrDelete(image, url)
        }
//        override suspend fun addToFavorite(url: String, protected: Boolean) {
//            cache.addOrDelete(url)
//        }

        override suspend fun preloadImage(url: String) {
           // imageLoader.load(url)
        }
    }
}