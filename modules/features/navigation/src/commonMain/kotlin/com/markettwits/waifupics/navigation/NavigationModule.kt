package com.markettwits.waifupics.navigation

import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.async.wrappers.RunAsync
import com.markettwits.async.wrappers.dispatchersList
import com.markettwits.cache.settings.WaifuPicsAppSettings
import com.markettwits.cache.settings.applicationSettingsModule
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