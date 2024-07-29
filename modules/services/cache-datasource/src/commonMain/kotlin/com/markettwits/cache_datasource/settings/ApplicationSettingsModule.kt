package com.markettwits.cache_datasource.settings

import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import com.markettwits.cache_datasource.kstore.storeOfWrapper
import io.github.xxfast.kstore.KStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val applicationSettingsModule = module {
    single<KStore<ApplicationSettings>> {
        storeOfWrapper(
            path = InStorageFileDirectory.path,
            fileName = "nekoSettings"
        )
    }
    singleOf(::WaifuPicsAppSettingsBase) bind WaifuPicsAppSettings::class
}