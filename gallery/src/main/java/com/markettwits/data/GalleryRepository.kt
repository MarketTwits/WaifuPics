package com.markettwits.data

import com.markettwits.presentation.detail.ImageFavoriteUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GalleryRepository {
    suspend fun observe(): Flow<List<ImageFavoriteUi>>
    suspend fun fetch(): List<ImageFavoriteUi>
    suspend fun delete(url: String, id: Long)
    suspend fun saveToGallery(url: String)
    class Base(
        private val dataSource: ImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper,
    ) : GalleryRepository {
        override suspend fun observe(): Flow<List<ImageFavoriteUi>> {
            return dataSource.observe().map {
                val items = it.map { cacheToUiMapper.map(it) }
                items.reversed()
            }
        }
        @Deprecated("use observe method instead of this")
        override suspend fun fetch() = dataSource.fetch().map { cacheToUiMapper.map(it) }
        override suspend fun delete(url: String, id: Long) {
            dataSource.delete(id, url)
        }

        override suspend fun saveToGallery(url: String) {
            dataSource.saveToGallery(url)
        }
    }
}