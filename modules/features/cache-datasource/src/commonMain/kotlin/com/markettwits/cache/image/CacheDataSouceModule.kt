package com.markettwits.cache.image

import com.markettwits.waifupics.cache.kstore.listStoreOfWrapper
import com.markettwits.waifupics.paths.cacheDirectory
import org.koin.dsl.module

val cacheDataSourceModule = module {
    val imagesCacheDataSource = ImagesCacheDataSource(
        listStoreOfWrapper(
            path = cacheDirectory("com.markettwits.waifupics"),
            fileName = "nekosApiImages"
        )
    )
    single<ImageCacheRepository> {
        ImageCacheRepository.Base(database = imagesCacheDataSource, ImageUiToCacheMapper.Base())
    }
}