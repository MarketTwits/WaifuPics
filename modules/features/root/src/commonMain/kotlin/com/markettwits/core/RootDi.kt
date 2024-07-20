package com.markettwits.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.markettwits.cloud_datasource.di.randomImageModule
import com.markettwits.di.galleryModule
import com.markettwits.di.navigationModule
import com.markettwits.navigation.BaseRouter
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.navigation.GalleryRouter
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.error.ApplicationAlreadyStartedException
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

fun waifuPicsAppDiContainer(scope: () -> Unit) = startKoin {
    modules(galleryModule, randomImageModule, rootNavigationModule, navigationModule)
    scope()
}

@Composable
fun initKoin(
    modules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {},
    content: @Composable () -> Unit
) = KoinApplication(application = {
    appDeclaration()
    modules(galleryModule, randomImageModule, rootNavigationModule, navigationModule)
    modules(modules)
}) {
    content()
}

fun initKoinApp(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(galleryModule, randomImageModule, rootNavigationModule, navigationModule)
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