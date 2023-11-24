package com.markettwits.waifupics.glue.gallery

import com.markettwits.navigation.LocalNavigationState
import com.markettwits.navigation.Screen
import com.markettwits.presentation.navigation.GalleryNavigation

class GalleryNavigationImpl :  GalleryNavigation{
    override fun toDetailImageScreen() {
        LocalNavigationState.rootNavigation.getNavController.navigate(
            Screen.GalleryItem.route()
        )
    }

    override fun pop() {
        LocalNavigationState.rootNavigation.getNavController.popBackStack()
    }
}