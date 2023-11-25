package com.markettwits.waifupics.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.markettwits.core_ui.LocalRootPadding
import com.markettwits.navigation.Graph
import com.markettwits.navigation.Screen
import com.markettwits.presentation.NavigationScreen
import com.markettwits.waifupics.navigation.LocalNavigationState
import com.markettwits.waifupics.navigation.MultiNavigationStates
import com.markettwits.waifupics.navigation.baseTabNavGraph
import com.markettwits.waifupics.navigation.detailsImage
import com.markettwits.waifupics.navigation.rememberMultiNavigationAppState

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
            NavigationScreen() {
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



