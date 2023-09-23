package com.markettwits.navigation.nav_grapth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.markettwits.waifupics.view.navigation.model.Screen


fun NavGraphBuilder.homeScreenNavGraph(
    mainScreen: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.Main.route(),
        route = Screen.Main.route()
    ){
        composable(Screen.Main.route()){
           mainScreen()
        }
        composable(Screen.Gallery.route()){
            Text(text = "Gallery")
        }
        composable(Screen.About.route()){
            Text(text = "About")
        }
    }
}