package com.markettwits.di

import com.markettwits.data.ImageRepository
import com.markettwits.data.cache.InStorageFileDirectory
import com.markettwits.data.cache.listStoreOfWrapper
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImagesCacheDataSource

object CacheDataSourceModule {

    private val repository: ImageRepository =
        ImageRepository.Base(
            database = ImagesCacheDataSource(
                listStoreOfWrapper(
                    path = InStorageFileDirectory.path,
                    fileName = "nekosApiImages"
                )
            ),
            uiToCache = ImageUiToCacheMapper.Base(),
        )

    fun provideImageRepository(): ImageRepository = repository
}