package com.markettwits.presentation

import androidx.compose.runtime.Composable
import com.markettwits.navigation.MultiNavigationAppState

interface HandleNavigation{
    @Composable
    fun Handle(state: MultiNavigationAppState)
}