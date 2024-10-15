package com.markettwits.image_action.api

import coil3.PlatformContext
import com.markettwits.image_action.impl.ImageIntentActionIos
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val imageActionModule: Module = module {
    single { PlatformContext.INSTANCE } bind PlatformContext::class
    single<ImageIntentAction.Mutable> { ImageIntentActionIos(get()) }
    single<ImageIntentAction.Action> { ImageIntentActionIos(get()) }
    single<ImageIntentAction.ShareImage> { ImageIntentActionIos(get()) }
    single<ImageLoader> { ImageLoader.Empty() }
}