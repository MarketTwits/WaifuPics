package com.markettwits.waifupics.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.waifupics.navigation.components.NavigationBody
import com.markettwits.waifupics.navigation.components.TopBarPanel
import kotlinx.coroutines.launch


@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    isShowTopBar: Boolean,
    onClickNavigationItem: (NavigationItem) -> Unit,
    content: @Composable () -> Unit
) {
    val viewModel: NavigationViewModel = ApplicationViewModel<NavigationViewModel.Base>()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isDarkTheme by viewModel.isDarkTheme().collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ,
        topBar = {
            AnimatedVisibility(isShowTopBar) {
                TopBarPanel(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                    isOpened = drawerState.isOpen,
                ) {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            }
        }) { paddingValues ->
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationBody(
                    isDarkTheme = isDarkTheme,
                    onClick = {
                        onClickNavigationItem(it)
                        scope.launch { drawerState.close() }
                    },
                    onClickChangeTheme = {
                        viewModel.onClickChangeTheme()
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                content()
            }
        }
    }
}



