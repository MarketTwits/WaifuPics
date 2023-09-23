package com.markettwits.waifupics.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.setContentLocal
import com.markettwits.navigation.view.NavigationScreen
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.main.ui.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLocal(savedInstanceState){
            WaifuPicsTheme {
                NavigationScreen(modifier = Modifier.fillMaxSize()){
                    MainScreen(it)
                }
            }
        }
    }
}

