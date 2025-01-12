package com.markettwits.waifupics.settings

import com.markettwits.waifupics.cache.kstore.storeOfWrapper
import com.markettwits.waifupics.paths.cacheDirectory
import io.github.xxfast.kstore.KStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val applicationSettingsModule = module {
    single<KStore<ApplicationSettings>> {
        storeOfWrapper(
            path = cacheDirectory("com.markettwits.waifupics"),
            fileName = "nekoSettings"
        )
    }
    singleOf(::WaifuPicsAppSettingsBase) bind WaifuPicsAppSettings::class
}