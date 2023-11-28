package com.markettwits.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import com.markettwits.core_ui.local_di.ApplicationViewModel
import com.markettwits.presentation.menu.NavigationBody
import com.markettwits.presentation.menu.TopBarPanel
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldScope
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import me.onebone.toolbar.rememberCollapsingToolbarState

@OptIn(ExperimentalMaterial3Api::class)
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
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars),
                drawerState = drawerState,
            ) {
                viewModel.toggleMenu(scope, drawerState)
            }
        }, content = {
            DrawerContent(
                drawerState = drawerState,
                onClick = {
                    viewModel.navigateTo(it)
                },
                content = {
                    content()
                }
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurfaceLayout(
    modifier: Modifier = Modifier,
    toolbar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val collapsedState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = collapsedState,
         //scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = { toolbar() },
        body = {
            content()
        })
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



