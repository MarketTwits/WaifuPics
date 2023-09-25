package com.markettwits.presentation

import androidx.compose.runtime.Composable
import com.markettwits.navigation.NavigationState

interface HandleNavigation{
    @Composable
    fun Handle(state: NavigationState)
}