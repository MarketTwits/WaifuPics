package com.markettwits.core_ui.system_theme.di

import com.markettwits.cache.settings.applicationSettingsModule
import com.markettwits.core_ui.system_theme.viewmodel.ThemeViewModel
import org.koin.dsl.module

val themeModule = module {
    includes(applicationSettingsModule)
    single<ThemeViewModel> { ThemeViewModel.Base(get(),) }
}