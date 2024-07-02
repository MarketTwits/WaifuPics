package com.markettwits.data

import com.markettwits.image_action.api.ImageLoader
import com.markettwits.presentation.screens.ImageFavoriteUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GalleryRepository {

    suspend fun observe(): Flow<ImageFavoriteUiState>

    suspend fun delete(id: Int)

    suspend fun delete(id: List<Int>, url: List<String>)

    suspend fun saveToGallery(url: String)

    class Base(
        private val dataSource: ImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper,
        private val imageLoader: ImageLoader,
    ) : GalleryRepository {

        override suspend fun observe(): Flow<ImageFavoriteUiState> = dataSource.observe().map {
            val items = it.map { cacheToUiMapper.map(it) }
            if (items.isEmpty())
                ImageFavoriteUiState.Empty
            else
                ImageFavoriteUiState.Base(items.reversed())
        }

        override suspend fun delete(id: Int) {
            dataSource.delete(id)
        }

        override suspend fun delete(id: List<Int>, url: List<String>) {
            dataSource.deleteList(id, url)
        }

        override suspend fun saveToGallery(url: String) {
            imageLoader.saveToGallery(url)
        }

    }
}