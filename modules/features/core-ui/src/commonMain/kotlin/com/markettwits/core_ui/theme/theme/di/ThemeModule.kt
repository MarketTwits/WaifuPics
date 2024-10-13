package com.markettwits.core_ui.theme.theme.di

import com.markettwits.cache.settings.applicationSettingsModule
import com.markettwits.core_ui.theme.theme.viewmodel.ThemeViewModel
import org.koin.dsl.module

val themeModule = module {
    includes(applicationSettingsModule)
    single<ThemeViewModel> { ThemeViewModel.Base(get(),) }
}