package com.markettwits.navigation

import androidx.compose.runtime.Composable

interface Container{
    @Composable
    fun Show() = Unit
    class MainContainer(screen: Screen) : Container
    class FullScreen(screen: Screen) : Container
}

