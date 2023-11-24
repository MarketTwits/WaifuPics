package com.markettwits.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.presentation.menu.NavigationBody
import com.markettwits.presentation.menu.TopBarPanel
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val viewModel: NavigationViewModel = ApplicationViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SurfaceLayout(
        toolbar = {
            TopBarPanel(
                drawerState = drawerState,
            ) {
                viewModel.toggleMenu(scope, drawerState)
            }
        }) {
        DrawerContent(
            drawerState = drawerState,
            content
        )
    }
}

@Composable
fun SurfaceLayout(
    modifier: Modifier = Modifier,
    toolbar: @Composable () -> Unit,
    content: @Composable () -> Unit,
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
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationBody {
                LocalNavigationState.baseNavigation.navigateTo(
                    it.screen.route()
                )
                scope.launch { drawerState.close() }
            }
        }
    ) {
        content()
    }
}


