package com.markettwits.waifupics.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.navigation.view.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaifuPicsTheme() {
                NavigationScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
