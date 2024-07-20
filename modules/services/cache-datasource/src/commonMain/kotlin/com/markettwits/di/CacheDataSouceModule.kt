package com.markettwits.di

import com.markettwits.data.ImageRepository
import com.markettwits.data.cache.InStorageFileDirectory
import com.markettwits.data.cache.listStoreOfWrapper
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.domain.ImageFavoriteCache
import io.github.xxfast.kstore.KStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

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
val cacheDataSourceModule = module {
    singleOf(::ImagesCacheDataSource)
    single<ImageRepository>{
        ImageRepository.Base(get(),get() )
    }
    single<ImageUiToCacheMapper> {
        ImageUiToCacheMapper.Base()
    }
    single<KStore<List<ImageFavoriteCache>>>{
        listStoreOfWrapper(
            path = InStorageFileDirectory.path,
            fileName = "nekosApiImages"
        )
    }
}