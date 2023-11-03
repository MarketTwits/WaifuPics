package com.markettwits.data

import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImageLoaderDataSource
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.models.ImageFavoriteCache

interface ImageRepository {
    suspend fun saveToGallery(url: String)
    suspend fun addToFavorite(url: String, ageRating: Boolean)
    suspend fun addOrDelete(url: String)
    suspend fun fetch(): List<ImageFavoriteCache>
    suspend fun delete(id: Long, url : String)
    suspend fun delete(imageUrl : String)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
        private val image : ImageLoaderDataSource

    ) : ImageRepository {
        override suspend fun fetch() = database.read().map { it.map() }
        override suspend fun saveToGallery(url: String) {
            image.saveToGallery(url)
        }
        override suspend fun addToFavorite(url: String, ageRating: Boolean) {
            val file = image.loadImage(url)
            database.write(uiToCache.map(url, file, ageRating))
        }

        override suspend fun addOrDelete(url: String) {
            if (database.hasImageWithUrl(url)) {
                database.delete(url)
                delete(url)
            } else {
                addToFavorite(url, false)
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