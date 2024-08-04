package com.markettwits.cache_datasource.image

import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import com.markettwits.cache_datasource.kstore.listStoreOfWrapper
import io.github.xxfast.kstore.KStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cacheDataSourceModule = module {
    val imagesCacheDataSource = ImagesCacheDataSource(
        listStoreOfWrapper(
            path = InStorageFileDirectory.path,
            fileName = "nekosApiImages"
        )
    )
    single<ImageRepository>{
        ImageRepository.Base(database = imagesCacheDataSource,ImageUiToCacheMapper.Base() )
    }
}