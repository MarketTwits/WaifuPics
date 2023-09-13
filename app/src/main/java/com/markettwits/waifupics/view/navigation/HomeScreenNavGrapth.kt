package com.markettwits.waifupics.view.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.markettwits.waifupics.view.navigation.model.Screen


fun NavGraphBuilder.homeScreenNavGraph(
//    homeScreenContent: @Composable () -> Unit,
//    galleryScreenContent : @Composable () -> Unit,
//    aboutScreenContent : @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.Main.route(),
        route = Screen.Main.route()
    ){
        composable(Screen.Main.route()){
           Text(text = "Main")
        }
        composable(Screen.Gallery.route()){
            Text(text = "Gallery")
        }
        composable(Screen.About.route()){
            Text(text = "About")
        }
    }
}