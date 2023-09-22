package com.markettwits.waifupics.view

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.MainComponentActivity
import com.markettwits.core_ui.setContentLocal
import com.markettwits.waifupics.navigation.view.NavigationScreen
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme


class MainActivity : MainComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLocal(savedInstanceState){
            WaifuPicsTheme {
                NavigationScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

