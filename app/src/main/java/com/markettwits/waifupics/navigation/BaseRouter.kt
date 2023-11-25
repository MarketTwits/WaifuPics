package com.markettwits.waifupics.navigation

import com.markettwits.navigation.Screen
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.navigation.GalleryRouter

class BaseRouter: GalleryRouter, NavigationRouter {
    override fun toDetailImageScreen() {
        LocalNavigationState.rootNavigation.getNavController.navigate(
            Screen.GalleryItem.route()
        )
    }
    override fun pop() {
        LocalNavigationState.rootNavigation.getNavController.popBackStack()
    }

    override fun navigateTo(route: String) {
        LocalNavigationState.baseNavigation.navigateTo(route)
    }
}