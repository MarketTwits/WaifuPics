package com.markettwits.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


@Stable
class NavigationState(
    private val navHostController: NavHostController
) {
    private var fullScreen = false
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
}


