package com.markettwits.data

import android.graphics.drawable.Drawable
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImageLoaderDataSource
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.models.ImageFavoriteCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ImageRepository {
    suspend fun observe() : Flow<List<ImageFavoriteCache>>
    suspend fun saveToGallery(url: String)
    suspend fun addToFavorite(drawable: Drawable, networkUrl : String,protected : Boolean)
    suspend fun addOrDelete(image: Drawable, url : String, protected: Boolean)
    suspend fun fetch(): List<ImageFavoriteCache>
    suspend fun delete(id: Long, url : String)
    suspend fun delete(imageUrl : String)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
        private val image : ImageLoaderDataSource

    ) : ImageRepository {
        override suspend fun fetch() = database.read().map { it.map() }
        override suspend fun observe(): Flow<List<ImageFavoriteCache>> {
            return  database.observeFavoriteImages().map { it.map { it.map() } }
        }

        override suspend fun saveToGallery(url: String) {
            image.saveToGallery(url)
        }
//        override suspend fun addToFavorite(url: String, ageRating: Boolean) {
//            val file = image.loadImage(url)
//            database.write(uiToCache.map(url, file, ageRating))
//            database.observeFavoriteImages()
//        }

        override suspend fun addToFavorite(drawable: Drawable,networkUrl : String, protected: Boolean) {
            val file = image.loadImage(drawable)
            database.write(uiToCache.map(networkUrl, file, protected))
            database.observeFavoriteImages()
        }

        override suspend fun addOrDelete(image: Drawable, url : String, protected: Boolean) {
            if (database.hasImageWithUrl(url)) {
                database.delete(url)
                delete(url)
            } else {
                addToFavorite(image, url, protected)
            }
        }

        override suspend fun delete(id: Long, url : String) {
            image.deleteImage(url)
            database.delete(id)
        }

        override suspend fun delete(imageUrl: String) {
            image.deleteImage(imageUrl)
            database.delete(imageUrl)
        }

        data class SavedImageInfo(
            val imagePath: String,
            val metadata: String
        )
    }
}