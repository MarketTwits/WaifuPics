package com.markettwits.waifupics.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.markettwits.navigation.Graph
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.navigation.MultiNavigationStates
import com.markettwits.navigation.Screen
import com.markettwits.navigation.rememberMultiNavigationAppState
import com.markettwits.presentation.NavigationScreen
import com.markettwits.waifupics.navigation.baseTabNavGraph
import com.markettwits.waifupics.navigation.detailsImage

@Composable
fun MenuNavGraph() {
    LocalNavigationState = MultiNavigationStates(
        rootNavigation = rememberMultiNavigationAppState(startDestination = Graph.Root.graph()),
        baseNavigation = rememberMultiNavigationAppState(startDestination = Screen.Main.route()),
        productNavigation = rememberMultiNavigationAppState(startDestination = Screen.GalleryItem.route())
    )
    NavHost(
        navController = LocalNavigationState.rootNavigation.getNavController,
        startDestination = Graph.Main.graph(),
        route = Graph.Root.graph(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Graph.Main.graph()) {
            LocalNavigationState.baseNavigation.setNavController(rememberNavController())
            NavigationScreen {
                NavHost(
                    navController = LocalNavigationState.baseNavigation.getNavController,
                    startDestination = Graph.Main.graph(),
                    route = Graph.Root.graph()
                ) {
                    baseTabNavGraph(
                        appState = LocalNavigationState.baseNavigation,
                        route = Graph.Main.graph()
                    )
                }
            }
        }
        composable(
            route = Screen.GalleryItem.route()
        ) {
            LocalNavigationState.productNavigation.setNavController(rememberNavController())
            NavHost(
                navController = LocalNavigationState.productNavigation.getNavController,
                startDestination = Screen.GalleryItem.route(),
                route = Graph.Root.graph()
            ) {
                detailsImage()
            }
        }
    }
}


