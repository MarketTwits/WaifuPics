package com.markettwits.cloud_datasource.di

import com.markettwits.cloud_datasource.data.cloud.base.HttpClientProviderBase
import com.markettwits.cloud_datasource.data.network.NekoService
import com.markettwits.cloud_datasource.data.network.NekoServiceBase
import com.markettwits.core_cloud.provider.JsonProviderBase

object NekoServiceCloudModule {

    fun provideNekoService(): NekoService = NekoServiceBase(
        httpClient = HttpClientProviderBase(
            json = JsonProviderBase().provide(),
            baseUrl = NekoServiceBase.BASE_URL
        )
    )
}