package com.markettwits.data

import android.util.Log
import com.markettwits.data.media_info.ExifServiceWrapper
import com.markettwits.data.media_info.ImageInfoToUiMapper
import com.markettwits.presentation.detail.ImageFavoriteUi
import com.markettwits.presentation.detail.info.MediaInfoUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GalleryRepository {
    suspend fun observe(): Flow<List<ImageFavoriteUi>>
    suspend fun fetch(): List<ImageFavoriteUi>
    suspend fun delete(url: String, id: Long)
    suspend fun saveToGallery(url: String)
    fun infoAboutImage(url: String) : MediaInfoUiState
    class Base(
        private val dataSource: ImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper,
        private val exifServiceWrapper: ExifServiceWrapper,
        private val imageInfoToUiMapper: ImageInfoToUiMapper
    ) : GalleryRepository {
        override suspend fun observe(): Flow<List<ImageFavoriteUi>> {
            return dataSource.observe().map {
                Log.d("mt05", "listGallery :$it")
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

        override fun infoAboutImage(url: String): MediaInfoUiState {
           val image =  exifServiceWrapper.imageInfo(url)
           return imageInfoToUiMapper.map(image)
        }
    }
}