package com.markettwits.presentation

import androidx.lifecycle.ViewModel
import com.markettwits.cache_datasource.settings.ColorTheme
import com.markettwits.cache_datasource.settings.WaifuPicsAppSettings
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import kotlinx.coroutines.flow.StateFlow

interface NavigationViewModel {

    fun isDarkTheme(): StateFlow<Boolean>

    fun navigateTo(route: String)

    fun onClickChangeTheme()

    class Base(
        private val navigation: NavigationRouter,
        private val settings: WaifuPicsAppSettings,
        private val themeCommunication: ApplicationThemeCommunication,
        private val runAsync: AsyncViewModel<Unit>,
    ) : NavigationViewModel, ViewModel() {

        override fun isDarkTheme(): StateFlow<Boolean> = themeCommunication.state()

        override fun navigateTo(route: String) {
            navigation.navigateTo(route)
        }

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
