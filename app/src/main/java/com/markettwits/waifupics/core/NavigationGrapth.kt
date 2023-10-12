package com.markettwits.waifupics.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.navigation.AppNavGraph
import com.markettwits.navigation.NavigationState
import com.markettwits.presentation.GalleryScreen
import com.markettwits.presentation.HandleNavigation
import com.markettwits.presentation.ImageScreenFull
import com.markettwits.`random-image`.ui.MainScreen
import com.markettwits.waifupics.about.AboutScreen


class BaseHandleNavigation : HandleNavigation {
    @Composable
    override fun Handle(state: NavigationState) {
        AppNavGraph(
            navHostController = state.navHostController,
            mainScreenContent = {
                MainScreen(firstRun = false)
            },
            galleryScreenContent = {
                GalleryScreen()
            },
            aboutScreenContent = {
               AboutScreen()
            },
            galleryItemScreenContent = {
                ImageScreenFull(Modifier, it)
            }
        )
    }
}
