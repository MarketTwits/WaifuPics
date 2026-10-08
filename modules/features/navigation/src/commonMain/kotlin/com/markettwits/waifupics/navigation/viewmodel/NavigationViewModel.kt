package com.markettwits.waifupics.navigation.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.AsyncViewModel
import com.markettwits.waifupics.settings.WaifuPicsAppSettings
import com.markettwits.waifupics.settings.params.ColorTheme

interface NavigationViewModel {

    fun onClickChangeTheme(isDarkTheme: Boolean)

    class Base(
        private val settings: WaifuPicsAppSettings,
        private val runAsync: AsyncViewModel<Unit>,
    ) : NavigationViewModel, ViewModel() {

        override fun onClickChangeTheme(isDarkTheme: Boolean) {
            runAsync.handleAsyncSingle {
                val currentSettings = settings.settings()
                if (isDarkTheme)
                    settings.updateSettings(currentSettings.copy(ColorTheme.LightTheme))
                else
                    settings.updateSettings(currentSettings.copy(ColorTheme.DarkTheme))
            }
        }
    }
}
