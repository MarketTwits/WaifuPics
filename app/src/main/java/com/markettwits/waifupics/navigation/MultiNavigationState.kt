package com.markettwits.waifupics.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberMultiNavigationAppState(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) = remember(navController, startDestination) {
    MultiNavigationAppState(navController, startDestination)
}

class MultiNavigationAppState(
    private var navController: NavHostController? = null,
    private val startDestination: String? = null,
) {

    fun setNavController(_navController: NavHostController) {
        navController = _navController
    }

    var getStartDestination: String = startDestination!!
        private set

    var getNavController: NavHostController = navController!!
        get() {
            return navController!!
        }
        private set

    fun setStartDestination(route: String) {
        getStartDestination = route
    }
    @Composable
    fun isRouteActive(route: String): Boolean {
        var navHostController = navController

        return if (navHostController != null) {
            val destination = navHostController.getDestination()
            return destination?.any {
                (it.route.equals(route))
            } ?: false
        } else {
            false
        }
    }

    fun navigateTo(route: String) {
        getNavController.navigate(route) {
            popUpTo(getNavController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

@Composable
fun NavHostController.getDestination(): Sequence<NavDestination>? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return currentDestination?.hierarchy
}

data class MultiNavigationStates(
    var rootNavigation: MultiNavigationAppState = MultiNavigationAppState(),
    var baseNavigation: MultiNavigationAppState = MultiNavigationAppState(),
    var productNavigation: MultiNavigationAppState = MultiNavigationAppState(),
)

lateinit var LocalNavigationState: MultiNavigationStates