package com.markettwits.navigation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.navigation.navigation_menu.NavigationBody
import com.markettwits.navigation.view.top_bar.TopBarPanel
import com.markettwits.waifupics.view.navigation.nav_grapth.NavGraph
import com.markettwits.waifupics.view.navigation.nav_grapth.rememberNavigationState
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    screen: @Composable (PaddingValues) -> Unit,
) {
    val viewModel: NavigationViewModel = ApplicationViewModel()
    val navigationState = rememberNavigationState()
    val navBackStackEntry =
        navigationState.navHostController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val collapsedState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        state = collapsedState,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            TopBarPanel(
                drawerState = drawerState,
            ) {
                viewModel.toggleMenu(scope, drawerState)
            }
        }) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { NavigationBody(){
                navigationState.navigateTo(it.screen.route())
                scope.launch { drawerState.close() }
            } }
        ) {
            NavGraph(
                paddingValues = PaddingValues(),
                navigationState = navigationState,
                screen = screen
            )
        }
    }
}




