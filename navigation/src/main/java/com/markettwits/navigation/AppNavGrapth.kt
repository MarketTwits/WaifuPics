package com.markettwits.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    galleryScreenContent: @Composable () -> Unit,
    galleryItemScreenContent: @Composable (String) -> Unit,
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
        composable(Screen.About.route()) {
            aboutScreenContent()
        }
        galleryScreenNavGraph(
            galleryScreenContent,
            galleryItemScreenContent
        )
    }
}

fun NavGraphBuilder.galleryScreenNavGraph(
    galleryScreenContent: @Composable () -> Unit,
    galleryItemScreenContent: @Composable (String) -> Unit
) {
    composable(Screen.Gallery.route()) {
        galleryScreenContent()
    }
    composable(
        route = "gallery_item/{image_id}",
        arguments = listOf(navArgument("image_id") { type = NavType.StringType})
    ) { backStackEntry ->
        val imageUrl = backStackEntry.arguments?.getString("image_id") ?: throw IllegalArgumentException("null")
        galleryItemScreenContent(imageUrl)
    }
}

