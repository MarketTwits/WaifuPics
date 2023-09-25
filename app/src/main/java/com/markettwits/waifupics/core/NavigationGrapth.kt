package com.markettwits.waifupics.core

import androidx.compose.runtime.Composable
import com.markettwits.navigation.AppNavGraph
import com.markettwits.navigation.NavigationState
import com.markettwits.presentation.HandleNavigation
import com.markettwits.`random-image`.ui.MainScreen
import com.markettwits.waifupics.about.AboutScreen
import com.markettwits.waifupics.gallery.GalleryScreen


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
            }
        )
    }
}
