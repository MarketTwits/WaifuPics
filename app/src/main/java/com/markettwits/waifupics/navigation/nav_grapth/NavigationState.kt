package com.markettwits.waifupics.view.navigation.nav_grapth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.markettwits.waifupics.navigation.nav_grapth.AppNavGraph
import com.markettwits.waifupics.view.main.ui.MainScreen

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
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) = remember { NavigationState(navHostController) }

@Composable
fun NavGraph(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    ) {
    AppNavGraph(
        navHostController = navigationState.navHostController,
        mainScreenContent = {
            MainScreen(paddingValues)
        },
        galleryScreenContent = {
            Text(text = "Gallery", color = Color.White)
        },
        aboutScreenContent = {
            Text(text = "About", color = Color.White)
        }
    )
}
