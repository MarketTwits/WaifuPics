package com.markettwits.data

import com.markettwits.models.ImageFavoriteCache
import java.io.File

interface FavoriteImageRepository {
    suspend fun saveToGallery(url: String)
    suspend fun add(url: String, ageRating: Boolean)
    suspend fun fetch(): List<ImageFavoriteCache>
    suspend fun delete(id: Long, url : String)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
        private val image : ImageLoaderDataSource

    ) : FavoriteImageRepository {
        override suspend fun fetch() = database.read().map { it.map() }
        override suspend fun saveToGallery(url: String) {
            image.saveToGallery(url)
        }
        override suspend fun add(url: String, ageRating: Boolean) {
            val file = image.loadImage(url)
            database.write(uiToCache.map(url, file, ageRating))
        }

        override suspend fun delete(id: Long, url : String) {
            val file = File(url)
            try {
                file.delete()
            }catch (e : Exception){
                throw RuntimeException("delete error :${e.cause}")
            }
            database.delete(id)
        }

        data class SavedImageInfo(
            val imagePath: String,
            val metadata: String
        )
    }
}