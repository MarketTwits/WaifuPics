package com.markettwits.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.presentation.menu.NavigationBody
import com.markettwits.presentation.menu.TopBarPanel
import kotlinx.coroutines.launch


@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val viewModel: NavigationViewModel.Base = ApplicationViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBarPanel(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars),
                drawerState = drawerState,
            ) {
                viewModel.toggleMenu(scope, drawerState)
            }
        }) { paddingValues ->
        DrawerContent(
            drawerState = drawerState,
            onClick = {
                viewModel.navigateTo(it)
            },
            content = {
                Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                    content()
                }
            }
        )
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



