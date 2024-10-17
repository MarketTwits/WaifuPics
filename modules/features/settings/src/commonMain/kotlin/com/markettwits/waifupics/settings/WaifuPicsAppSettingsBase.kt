package com.markettwits.waifupics.settings

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WaifuPicsAppSettingsBase(
    private val store: KStore<ApplicationSettings>
) : WaifuPicsAppSettings {

    override suspend fun settings(): ApplicationSettings =
        store.get() ?: ApplicationSettings.defaultApplicationSettings

    override suspend fun updateSettings(applicationSettings: ApplicationSettings) {
        store.set(applicationSettings)
    }

    override suspend fun observeSettings(): Flow<ApplicationSettings> =
        store.updates.map { it ?: ApplicationSettings.defaultApplicationSettings }
}