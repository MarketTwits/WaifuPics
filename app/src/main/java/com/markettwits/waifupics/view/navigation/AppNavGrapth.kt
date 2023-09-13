package com.markettwits.waifupics.view.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.markettwits.waifupics.view.navigation.model.Screen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    galleryScreenContent: @Composable () -> Unit,
    aboutScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Main.route(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Main.route()) {
            mainScreenContent()
        }
        composable(Screen.Gallery.route()) {
            galleryScreenContent()
        }
        composable(Screen.About.route()) {
            aboutScreenContent()
        }
    }
}