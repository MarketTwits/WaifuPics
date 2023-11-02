package com.markettwits.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Stable
class NavigationState(
    val navHostController: NavHostController
) {
    var fullScreen = false
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
        fullScreen = false
    }
    fun navigateToImage(imageId: String){
        navHostController.navigate(Screen.GalleryItem.routeId(imageId))
        fullScreen = true
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) = remember { NavigationState(navHostController) }


