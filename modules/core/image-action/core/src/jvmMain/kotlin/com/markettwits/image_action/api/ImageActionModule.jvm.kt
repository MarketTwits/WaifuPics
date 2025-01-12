package com.markettwits.image_action.api

import coil3.PlatformContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val imageActionModule: Module = module {
    single { PlatformContext.INSTANCE } bind PlatformContext::class
    single<ImageIntentAction.Mutable> { ImageIntentActionJvm(get()) }
    single<ImageIntentAction.Action> { ImageIntentActionJvm(get()) }
    single<ImageIntentAction.ShareImage> { ImageIntentActionJvm(get()) }
    single<ImageLoader> { ImageLoaderJvm(get()) }
}