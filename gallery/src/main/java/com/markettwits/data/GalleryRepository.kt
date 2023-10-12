package com.markettwits.data
import com.markettwits.presentation.ImageFavoriteUi

interface GalleryRepository {
    suspend fun fetch() : List<ImageFavoriteUi>
    suspend fun delete(item : List<Long>)
    class Base(
        private val dataSource: FavoriteImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper
    ) : GalleryRepository{
        override suspend fun fetch() = dataSource.fetch().map { cacheToUiMapper.map(it) }
        override suspend fun delete(item : List<Long>) {
            //dataSource.delete(item)
        }
    }
}