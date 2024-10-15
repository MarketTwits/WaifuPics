package com.markettwits.waifupics.root.core

import com.markettwits.core_ui.system_theme.di.themeModule
import com.markettwits.waifupics.random_image.di.randomImageModule
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
