package com.markettwits.waifupics.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.navigation.MultiNavigationAppState
import com.markettwits.navigation.Screen
import com.markettwits.presentation.detail.ImageScreenFull
import com.markettwits.presentation.list.GalleryScreen
import com.markettwits.random_image.ui.MainScreen
import com.markettwits.waifupics.about.AboutScreen

fun NavGraphBuilder.baseTabNavGraph(
    route: String,
    appState: MultiNavigationAppState
) {
    navigation(
        startDestination = appState.getStartDestination,
        route = route
    ) {
        composable(route = Screen.Main.route()) {
            MainScreen(firstRun = false)
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
        arguments = listOf(navArgument(Screen.GalleryItem.route()) {
            type = NavType.StringType
        }),
    ) { backStackEntry ->
        val imageUrl =
            LocalNavigationState.rootNavigation.getNavController.currentBackStackEntry?.arguments?.getString(
                Screen.GalleryItem.route()
            )
                ?: ""
        ImageScreenFull()
    }
}

