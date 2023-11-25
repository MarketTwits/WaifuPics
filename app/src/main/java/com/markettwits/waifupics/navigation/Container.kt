package com.markettwits.waifupics.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.markettwits.navigation.Screen
import com.markettwits.presentation.screens.detail.ImageScreenFull
import com.markettwits.presentation.screens.list.GalleryScreen
import com.markettwits.random_image.presentation.random_image_screen.MainScreen
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
            MainScreen()
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
        ImageScreenFull()
    }
}

