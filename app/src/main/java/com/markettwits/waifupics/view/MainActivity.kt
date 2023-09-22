package com.markettwits.waifupics.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.navigation.view.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLocal(savedInstanceState = savedInstanceState) {
            WaifuPicsTheme() {
                NavigationScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}





val LocalBundle = compositionLocalOf<Bundle?> { error("Bundle is not available") }
fun ComponentActivity.setContentLocal(
    savedInstanceState: Bundle?,
    content: @Composable () -> Unit,
) {
    setContent {
        CompositionLocalProvider(LocalBundle provides savedInstanceState) {
            content()
        }
    }
}
