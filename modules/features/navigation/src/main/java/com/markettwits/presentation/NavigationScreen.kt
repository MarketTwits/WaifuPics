package com.markettwits.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.local_di.ApplicationViewModel
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
    val viewModel: NavigationViewModel.Base = ApplicationViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SurfaceLayout(
        toolbar = {
            TopBarPanel(
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                drawerState = drawerState,
            ) {
                viewModel.toggleMenu(scope, drawerState)
            }
        }) {
        DrawerContent(
            drawerState = drawerState,
            onClick = {
                viewModel.navigateTo(it)
            },
            content = {
                content()
            }
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
//        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = { toolbar() }) {
        content()
    }
}

@Composable
fun DrawerContent(
    drawerState: DrawerState,
    content: @Composable () -> Unit,
    onClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationBody {
                onClick(it.screen.route())
                scope.launch { drawerState.close() }
            }
        }
    ) {
        content()
    }
}


