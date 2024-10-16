package com.markettwits.core_ui.provider

import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import com.markettwits.core_ui.components.image.asyncImageLoader
import com.markettwits.theme.viewmodel.ThemeViewModel

@Composable
fun SetContentLocal(
    isDarkTheme: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    setSingletonImageLoaderFactory { context ->
        context.asyncImageLoader()
    }

    com.markettwits.theme.components.WaifuPicsTheme(
        viewModel = ApplicationViewModel<ThemeViewModel>(),
        isDarkTheme = isDarkTheme,
        content = content
    )
}
