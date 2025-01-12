package com.markettwits.theme.di

import com.markettwits.theme.viewmodel.ThemeViewModel
import com.markettwits.waifupics.settings.applicationSettingsModule
import org.koin.dsl.module

val themeModule = module {
    includes(applicationSettingsModule)
    single<ThemeViewModel> { ThemeViewModel.Base(get(),) }
}