package com.markettwits.navigation

import androidx.compose.runtime.Composable

interface Container{
    fun Show(content: @Composable () -> Unit) = Unit
    object WithTopBar : Container
    object FullScreen : Container
}

