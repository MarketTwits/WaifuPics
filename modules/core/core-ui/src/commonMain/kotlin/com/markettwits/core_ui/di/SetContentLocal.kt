package com.markettwits.core_ui.di

import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import com.markettwits.core_ui.components.image.asyncImageLoader
import com.markettwits.core_ui.theme.WaifuPicsTheme

@Composable
fun SetContentLocal(
    isDarkTheme: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    setSingletonImageLoaderFactory { context ->
        context.asyncImageLoader()
    }
    WaifuPicsTheme(isDarkTheme) {
        content()
    }
}
