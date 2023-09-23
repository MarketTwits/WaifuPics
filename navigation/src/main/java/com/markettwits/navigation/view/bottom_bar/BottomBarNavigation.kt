package com.markettwits.waifupics.navigation.view.bottom_bar

import androidx.compose.runtime.Composable
import com.markettwits.navigation.model.NavigationItem
import com.markettwits.waifupics.view.navigation.nav_grapth.NavigationState

@Composable
fun BottomBarNavigation(
    selectedItem : List<NavigationItem>,
    navigationState : NavigationState,
    updateState : (NavigationItem) -> Unit
)
{
    NavigationPanel(
        list = selectedItem,
        navItemClick = {
            if (!it.isSelected) {
                navigationState.navigateTo(it.screen.route())
            }
            updateState(it)
        }
    )
}