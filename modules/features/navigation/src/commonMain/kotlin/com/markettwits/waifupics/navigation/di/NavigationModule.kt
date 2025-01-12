package com.markettwits.waifupics.navigation.di

import com.markettwits.async.AsyncViewModel
import com.markettwits.async.RunAsync
import com.markettwits.async.dispatchersList
import com.markettwits.waifupics.navigation.viewmodel.ApplicationThemeCommunication
import com.markettwits.waifupics.navigation.viewmodel.NavigationViewModel
import com.markettwits.waifupics.settings.WaifuPicsAppSettings
import com.markettwits.waifupics.settings.applicationSettingsModule
import org.koin.dsl.module


val navigationModule = module {
    includes(applicationSettingsModule)
    single<NavigationViewModel.Base> {
        NavigationViewModel.Base(
            settings = get<WaifuPicsAppSettings>(),
            themeCommunication = ApplicationThemeCommunication.Base(),
            runAsync = AsyncViewModel.Base(
                RunAsync.Base(dispatchersList)
            )
        )
    }
}