package com.markettwits.waifupics.root.core

import com.markettwits.theme.di.themeModule
import com.markettwits.waifupics.gallery.di.galleryModule
import com.markettwits.waifupics.navigation.navigationModule
import com.markettwits.waifupics.random.di.randomImageModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoinApp(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(galleryModule, randomImageModule, navigationModule, themeModule)
}
