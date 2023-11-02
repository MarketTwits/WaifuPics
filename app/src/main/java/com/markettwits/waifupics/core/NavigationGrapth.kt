package com.markettwits.waifupics.core

import androidx.compose.runtime.Composable
import com.markettwits.navigation.MultiNavigationAppState
import com.markettwits.presentation.HandleNavigation


class BaseHandleNavigation : HandleNavigation {
    @Composable
    override fun Handle(state: MultiNavigationAppState) {
        MenuNavGraph()
    }
}
