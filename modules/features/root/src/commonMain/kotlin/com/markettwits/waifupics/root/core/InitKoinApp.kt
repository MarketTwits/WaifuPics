package com.markettwits.waifupics.root.core

import com.markettwits.core_ui.theme.theme.di.themeModule
import com.markettwits.waifupics.cloud_datasource.di.randomImageModule
import com.markettwits.waifupics.navigation.navigationModule
import com.markettwits.waifupics.gallery.di.galleryModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoinApp(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(galleryModule, randomImageModule, navigationModule, themeModule)
}
