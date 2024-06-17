package com.markettwits.data

import com.markettwits.data.media_info.ExifServiceWrapper
import com.markettwits.data.media_info.ImageInfoToUiMapper
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.presentation.screens.ImageFavoriteUiState
import com.markettwits.presentation.screens.detail.info.MediaInfoUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GalleryRepository {

    suspend fun observe(): Flow<ImageFavoriteUiState>

    suspend fun delete(url: String, id: Long)

    suspend fun delete(id: List<Long>, url: List<String>)

    suspend fun saveToGallery(url: String)

    fun infoAboutImage(url: String): MediaInfoUiState

    class Base(
        private val dataSource: ImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper,
        private val imageLoader: ImageLoader,
        private val exifServiceWrapper: ExifServiceWrapper,
        private val imageInfoToUiMapper: ImageInfoToUiMapper
    ) : GalleryRepository {
        override suspend fun observe(): Flow<ImageFavoriteUiState> =
            dataSource.observe().map {
                val items = it.map { cacheToUiMapper.map(it) }
                if (items.isEmpty())
                    ImageFavoriteUiState.Empty
                else
                    ImageFavoriteUiState.Base(items.reversed())
            }

        override suspend fun delete(url: String, id: Long) {
            dataSource.delete(id, url)
        }

        override suspend fun delete(id: List<Long>, url: List<String>) {
            dataSource.deleteList(id, url)
        }

        override suspend fun saveToGallery(url: String) {
            imageLoader.saveToGallery(url)
        }

        override fun infoAboutImage(url: String): MediaInfoUiState {
            val image = exifServiceWrapper.imageInfo(url)
            return imageInfoToUiMapper.map(image)
        }
    }
}