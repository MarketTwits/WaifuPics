package com.markettwits.waifupics.random.cloud

import com.markettwits.waifupics.cloud.http.HttpClientProvider
import com.markettwits.waifupics.cloud.http.HttpClientProviderBase
import com.markettwits.waifupics.cloud.json.JsonProviderBase
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val cloudDataSourceModule = module{
    singleOf(::NekoServiceBase) bind NekoService::class
    singleOf(::HttpClientProviderBase) bind HttpClientProvider::class
    single<Json>{
        JsonProviderBase().provide()
    }
    single<String>{
        NekoServiceBase.Companion.BASE_URL
    }
}