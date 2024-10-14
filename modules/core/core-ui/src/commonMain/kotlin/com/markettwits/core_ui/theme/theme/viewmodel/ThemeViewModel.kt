package com.markettwits.core_ui.theme.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.cache.settings.ColorTheme
import com.markettwits.cache.settings.WaifuPicsAppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ThemeViewModel {

    fun theme(): StateFlow<ColorTheme>

    class Base(
        private val settings: WaifuPicsAppSettings
    ) : ThemeViewModel, ViewModel() {

        private val scope = CoroutineScope(Dispatchers.Main.immediate)

        private val theme = MutableStateFlow<ColorTheme>(ColorTheme.Empty)

        init {
            scope.launch {
                settings.observeSettings().collect {
                    theme.value = it.theme
                }
            }
        }

        override fun theme(): StateFlow<ColorTheme> = theme

    }
}