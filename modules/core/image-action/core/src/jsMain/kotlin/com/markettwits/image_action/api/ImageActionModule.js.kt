package com.markettwits.image_action.api

import com.markettwits.image_action.impl.ImageIntentActionJS
import com.markettwits.image_action.impl.ImageLoaderJS
import org.koin.dsl.module

actual val imageActionModule = module {
    single<ImageIntentAction.Mutable> { ImageIntentActionJS(get()) }
    single<ImageIntentAction.Action> { ImageIntentActionJS(get()) }
    single<ImageIntentAction.ShareImage> { EmptyIntentAction() }
    single<ImageLoader> { ImageLoaderJS() }
}
