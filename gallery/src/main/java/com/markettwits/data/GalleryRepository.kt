package com.markettwits.data

import com.markettwits.presentation.detail.ImageFavoriteUi

interface GalleryRepository {
    suspend fun fetch(): List<ImageFavoriteUi>
    suspend fun delete(url : String, id : Long)
    suspend fun saveToGallery(url: String)
    class Base(
        private val dataSource: FavoriteImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper
    ) : GalleryRepository {
        override suspend fun fetch() = dataSource.fetch().map { cacheToUiMapper.map(it) }
        override suspend fun delete(url: String, id : Long) {
            dataSource.delete(id, url)
        }

        override suspend fun saveToGallery(url: String) {
            dataSource.saveToGallery(url)
        }
    }
}