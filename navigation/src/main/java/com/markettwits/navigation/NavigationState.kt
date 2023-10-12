package com.markettwits.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
    fun navigateToImage(imageId: String){
        navHostController.navigate("gallery_item/$imageId")
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) = remember { NavigationState(navHostController) }

