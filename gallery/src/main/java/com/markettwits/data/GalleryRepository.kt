package com.markettwits.data
import com.markettwits.database.FavoriteImageRepository
import com.markettwits.presentation.ImageFavoriteUi

interface GalleryRepository {
    suspend fun fetch() : List<ImageFavoriteUi>
    class Base(
        private val dataSource: FavoriteImageRepository,
        private val cacheToUiMapper: FavoriteImageCacheToUiMapper
    ) : GalleryRepository{
        override suspend fun fetch() = dataSource.fetch().map { cacheToUiMapper.map(it) }
    }
}