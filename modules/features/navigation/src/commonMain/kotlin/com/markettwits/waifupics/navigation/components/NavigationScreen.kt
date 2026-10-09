package com.markettwits.waifupics.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.LocalWaifuDarkTheme
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.navigation.model.NavigationItem
import com.markettwits.waifupics.navigation.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun NavigationScreen(
    modifier: Modifier = Modifier,
    isShowTopBar: Boolean,
    overlayTopBar: Boolean = false,
    selectedTitle: String = "Home",
    onClickNavigationItem: (NavigationItem) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val viewModel: NavigationViewModel = ApplicationViewModel<NavigationViewModel.Base>()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isDarkTheme = LocalWaifuDarkTheme.current
    val scope = rememberCoroutineScope()
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            AnimatedVisibility(isShowTopBar && !overlayTopBar) {
                TopBarPanel(
                    isOpened = drawerState.isOpen,
                ) {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            }
        }) { paddingValues ->
        Box(Modifier.fillMaxSize()) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                gesturesEnabled = isShowTopBar,
                drawerContent = {
                    NavigationBody(
                        contentPadding = PaddingValues(
                            top = paddingValues.calculateTopPadding() + if (overlayTopBar) 56.dp else 0.dp
                        ),
                        isDarkTheme = isDarkTheme,
                        selectedTitle = selectedTitle,
                        onClick = {
                            onClickNavigationItem(it)
                            scope.launch { drawerState.close() }
                        },
                        onClickChangeTheme = {
                            viewModel.onClickChangeTheme(isDarkTheme)
                        }
                    )
                }
            ) {
                content(paddingValues)
            }
            if (isShowTopBar && overlayTopBar) {
                TopBarPanel(isOpened = drawerState.isOpen, overlay = true) {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            }

        }
    }
}
