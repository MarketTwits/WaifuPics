package com.markettwits.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.markettwits.AboutScreen
import com.markettwits.cloud_datasource.presentation.random_image.components.RandomImageScreen
import com.markettwits.presentation.screens.detail.ImageScreenFull
import com.markettwits.presentation.screens.list.GalleryScreen
import com.markettwits.waifupics.navigation.MultiNavigationAppState

fun NavGraphBuilder.baseTabNavGraph(
    route: String,
    appState: MultiNavigationAppState
) {
    navigation(
        startDestination = appState.getStartDestination,
        route = route
    ) {
        composable(route = Screen.Main.route()) {
            RandomImageScreen()
        }
        composable(route = Screen.About.route()) {
            AboutScreen()
        }
        composable(route = Screen.Gallery.route()) {
            GalleryScreen()
        }
    }
}

fun NavGraphBuilder.detailsImage() {
    composable(
        route = Screen.GalleryItem.route(),
    ) { backStackEntry ->
        ImageScreenFull()
    }
}

