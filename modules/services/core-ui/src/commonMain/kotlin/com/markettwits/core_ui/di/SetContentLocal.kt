package com.markettwits.core_ui.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import com.markettwits.core.di.ProvideViewModel
import com.markettwits.core_ui.image.ImageLoader
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.core_ui.image.asyncImageLoader
import com.markettwits.core_ui.theme.WaifuPicsTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SetContentLocal(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalImageLoader provides ImageLoader.Base(LocalPlatformContext.current),
    ) {
        setSingletonImageLoaderFactory { context ->
            context.asyncImageLoader()
        }
        WaifuPicsTheme {
            content()
        }
    }
}
