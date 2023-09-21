package com.markettwits.waifupics.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.navigation.view.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (savedInstanceState == null){
                clearCache(this, true, true)
            }
            WaifuPicsTheme() {
                NavigationScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
fun clearCache(context: Context, memory: Boolean = true, file: Boolean = true) {
    // 1) clear memory cache
    if (memory) {
        val imageLoader = context.imageLoader
        imageLoader.memoryCache?.clear()
    }
    // 2) clear file cache
    if (file) {
        val cache = ImageLoader(context).diskCache
        cache?.clear()
    }
}

