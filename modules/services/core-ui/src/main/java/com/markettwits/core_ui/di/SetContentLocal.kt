package com.markettwits.core_ui.di

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.markettwits.core_ui.image.ImageLoader
import com.markettwits.core_ui.image.LocalImageLoader

val LocalBundle = compositionLocalOf<Bundle?> { error("Bundle is not available") }
val LocalRootPadding = compositionLocalOf<PaddingValues> { error("Padding is not available")  }
fun ComponentActivity.setContentLocal(
    savedInstanceState: Bundle?,
    content: @Composable () -> Unit,
) {
    setContent {
        CompositionLocalProvider(
            LocalBundle provides savedInstanceState,
            LocalImageLoader provides ImageLoader.Base(
            LocalContext.current)
        ) {
            content()
        }
    }
}
