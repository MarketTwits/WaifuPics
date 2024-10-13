package com.markettwits.waifupics.navigation

import androidx.lifecycle.ViewModel
import com.markettwits.cache.settings.ColorTheme
import com.markettwits.cache.settings.WaifuPicsAppSettings
import com.markettwits.async.communication.StateCommunication
import com.markettwits.async.wrappers.AsyncViewModel
import kotlinx.coroutines.flow.StateFlow

interface NavigationViewModel {

    fun isDarkTheme(): StateFlow<Boolean>

    fun onClickChangeTheme()

    class Base(
        private val settings: WaifuPicsAppSettings,
        private val themeCommunication: ApplicationThemeCommunication,
        private val runAsync: AsyncViewModel<Unit>,
    ) : NavigationViewModel, ViewModel() {

        override fun isDarkTheme(): StateFlow<Boolean> = themeCommunication.state()

        override fun onClickChangeTheme() {
            runAsync.handleAsyncSingle {
                val currentSettings = settings.settings()
                if (currentSettings.theme is ColorTheme.DarkTheme)
                    settings.updateSettings(currentSettings.copy(ColorTheme.LightTheme))
                else
                    settings.updateSettings(currentSettings.copy(ColorTheme.DarkTheme))
            }
        }
    }
}

interface ApplicationThemeCommunication : StateCommunication.Mutable<Boolean> {
    class Base : StateCommunication.Abstract<Boolean>(false),
        ApplicationThemeCommunication
}
