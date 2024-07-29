package com.markettwits.core

import com.markettwits.cloud_datasource.di.randomImageModule
import com.markettwits.core_ui.theme.theme.di.themeModule
import com.markettwits.di.galleryModule
import com.markettwits.di.navigationModule
import com.markettwits.navigation.BaseRouter
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.navigation.GalleryRouter
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoinApp(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(galleryModule, randomImageModule, rootNavigationModule, navigationModule, themeModule)
}

internal val rootNavigationModule = module {
    val baseRouter = BaseRouter()
    single<GalleryRouter> {
        baseRouter
    }
    single<NavigationRouter> {
        baseRouter
    }
}