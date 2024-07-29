package com.markettwits.cache_datasource.image

import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import com.markettwits.cache_datasource.kstore.listStoreOfWrapper
import io.github.xxfast.kstore.KStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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