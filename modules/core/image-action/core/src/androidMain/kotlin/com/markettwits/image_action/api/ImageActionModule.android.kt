package com.markettwits.image_action.api

import android.content.Context
import com.markettwits.image_action.impl.ImageFileWrapper
import com.markettwits.image_action.impl.ImageIntentActionImpl
import com.markettwits.image_action.impl.ImageLoaderImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

actual val imageActionModule: Module = module {
    val context: Context by KoinJavaComponent.inject(Context::class.java)
    single<ImageFileWrapper> { ImageFileWrapper.Base(context) }
    single<ImageFileWrapper.Base> { ImageFileWrapper.Base(context) }
    single<ImageIntentAction.Mutable> { ImageIntentActionImpl(context,get()) }
    single<ImageIntentAction.Action> { ImageIntentActionImpl(context,get()) }
    single<ImageIntentAction.ShareImage> { ImageIntentActionImpl(context,get()) }
    single<ImageLoader> { ImageLoaderImpl(context,get()) }
}