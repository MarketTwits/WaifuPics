package com.markettwits.cache_datasource.settings

import kotlinx.coroutines.flow.Flow

interface WaifuPicsAppSettings {

    suspend fun settings() : ApplicationSettings

    suspend fun updateSettings(applicationSettings: ApplicationSettings)

    suspend fun observeSettings() : Flow<ApplicationSettings>

}