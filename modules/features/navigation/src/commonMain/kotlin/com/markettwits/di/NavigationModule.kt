package com.markettwits.di

import com.markettwits.cache_datasource.settings.applicationSettingsModule
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.core.wrappers.dispatchersList
import com.markettwits.presentation.ApplicationThemeCommunication
import com.markettwits.presentation.NavigationViewModel
import org.koin.dsl.module


val navigationModule = module {
    includes(applicationSettingsModule)
    single<NavigationViewModel.Base> {
        NavigationViewModel.Base(
            get(), get(),
            ApplicationThemeCommunication.Base(),
            AsyncViewModel.Base(
                RunAsync.Base(
                    dispatchersList
                )
            )
        )
    }
}