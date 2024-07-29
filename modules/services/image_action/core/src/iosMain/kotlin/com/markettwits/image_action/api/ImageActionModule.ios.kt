package com.markettwits.image_action.api

import org.koin.core.module.Module
import org.koin.dsl.module

actual val imageActionModule: Module = module {
    single<ImageIntentAction.Mutable> { EmptyIntentAction() }
    single<ImageIntentAction.Action> { EmptyIntentAction() }
    single<ImageIntentAction.ShareImage> { EmptyIntentAction() }
    single<ImageLoader> { ImageLoader.Empty() }
}