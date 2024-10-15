package com.markettwits.image_action.api

import com.markettwits.image_action.impl.ImageIntentActionWASMJS
import com.markettwits.image_action.impl.ImageLoaderWASMJS
import org.koin.core.module.Module
import org.koin.dsl.module

actual val imageActionModule: Module = module {
    single<ImageIntentAction.Mutable> { ImageIntentActionWASMJS(get()) }
    single<ImageIntentAction.Action> { ImageIntentActionWASMJS(get()) }
    single<ImageIntentAction.ShareImage> { ImageIntentActionWASMJS(get()) }
    single<ImageLoader> { ImageLoaderWASMJS() }
}