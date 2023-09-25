package com.markettwits.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
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
import com.markettwits.navigation.NavigationState
import com.markettwits.navigation.rememberNavigationState
import com.markettwits.presentation.top_bar.NavigationBody
import com.markettwits.presentation.top_bar.TopBarPanel
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: NavigationViewModel = ApplicationViewModel()
    val navigationState = rememberNavigationState()
    val navBackStackEntry =
        navigationState.navHostController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    SurfaceLayout(toolbar = {
        TopBarPanel(
            drawerState = drawerState,
        ) { viewModel.toggleMenu(scope, drawerState) }
    }) {
        DrawerContent(
            drawerState = drawerState,
            navigationState = navigationState,
            viewModel = viewModel
        )
    }
}

@Composable
fun SurfaceLayout(
    modifier: Modifier = Modifier,
    toolbar: @Composable() (CollapsingToolbarScope.() -> Unit),
    content: @Composable () -> Unit
) {
    val collapsedState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        state = collapsedState,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = { toolbar() }) {
        content()
    }
}

@Composable
fun DrawerContent(
    drawerState: DrawerState,
    navigationState: NavigationState,
    viewModel: NavigationViewModel
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationBody() {
                navigationState.navigateTo(it.screen.route())
                scope.launch { drawerState.close() }
            }
        }
    ) {
        viewModel.handleNavigation().Handle(navigationState)
    }
}



