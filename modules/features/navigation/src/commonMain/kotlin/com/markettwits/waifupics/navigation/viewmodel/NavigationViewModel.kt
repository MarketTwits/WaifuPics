package com.markettwits.waifupics.navigation.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.AsyncViewModel
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.settings.WaifuPicsAppSettings
import com.markettwits.waifupics.settings.params.ColorTheme
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
