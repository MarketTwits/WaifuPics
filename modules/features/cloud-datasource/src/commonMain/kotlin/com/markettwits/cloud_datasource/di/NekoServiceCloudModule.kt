package com.markettwits.cloud_datasource.di

import com.markettwits.cloud_datasource.data.cloud.base.HttpClientProvider
import com.markettwits.cloud_datasource.data.cloud.base.HttpClientProviderBase
import com.markettwits.cloud_datasource.data.network.NekoService
import com.markettwits.cloud_datasource.data.network.NekoServiceBase
import com.markettwits.core_cloud.provider.JsonProviderBase
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object NekoServiceCloudModule {

    fun provideNekoService(): NekoService = NekoServiceBase(
        httpClient = HttpClientProviderBase(
            json = JsonProviderBase().provide(),
            baseUrl = NekoServiceBase.BASE_URL
        )
    )
}
val cloudDataSourceModule = module{
    singleOf(::NekoServiceBase) bind NekoService::class
    singleOf(::HttpClientProviderBase) bind HttpClientProvider::class
    single<Json>{
        JsonProviderBase().provide()
    }
    single<String>{
        NekoServiceBase.BASE_URL
    }
}