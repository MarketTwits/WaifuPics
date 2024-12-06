package com.markettwits.waifupics.root.core

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.markettwits.waifupics.about.AboutScreen
import com.markettwits.waifupics.gallery.item.components.ImageScreenFull
import com.markettwits.waifupics.gallery.items.components.GalleryScreen
import com.markettwits.waifupics.navigation.components.NavigationScreen
import com.markettwits.waifupics.navigation.model.NavigationItem
import com.markettwits.waifupics.random.components.RandomImageScreen


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationScreen(
        isShowTopBar = navBackStackEntry?.destination?.route != Screen.GalleryItem.route(),
        onClickNavigationItem = {
            navController.navigate(it.mapToScreen().route())
        }, content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Main.route(),
                modifier = modifier
            ) {
                composable(Screen.Main.route()) {
                    RandomImageScreen(paddingValues = paddingValues)
                }
                composable(Screen.About.route()) {
                    AboutScreen(paddingValues = paddingValues)
                }
                composable(Screen.Gallery.route()) {
                    GalleryScreen(onClickGoDetail = {
                        navController.navigate(Screen.GalleryItem.route())
                    }, paddingValues
                    )
                }
                composable(Screen.GalleryItem.route()) {
                    ImageScreenFull {
                        navController.popBackStack()
                    }
                }
            }
        })
}

private fun NavigationItem.mapToScreen(): Screen =
    when (this) {
        is NavigationItem.About -> Screen.About
        is NavigationItem.Favorite -> Screen.Gallery
        is NavigationItem.Home -> Screen.Main
    }



