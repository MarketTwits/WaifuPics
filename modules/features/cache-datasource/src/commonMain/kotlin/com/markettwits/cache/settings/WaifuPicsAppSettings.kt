package com.markettwits.cache.settings

import kotlinx.coroutines.flow.Flow

interface WaifuPicsAppSettings {

    suspend fun settings() : ApplicationSettings

    suspend fun updateSettings(applicationSettings: ApplicationSettings)

    suspend fun observeSettings() : Flow<ApplicationSettings>

}