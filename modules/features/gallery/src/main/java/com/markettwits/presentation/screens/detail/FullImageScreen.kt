package com.markettwits.presentation.screens.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.base_extensions.noRippleClickable
import com.markettwits.presentation.screens.detail.bottomBar.DownBarScreenImage
import com.markettwits.presentation.screens.detail.image.ZoomablePagerImage
import com.markettwits.presentation.screens.detail.topbar.TopBarScreenImage
import com.markettwits.presentation.screens.detail.window_controller.rememberWindowInsetsController
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
) {
    val zoomState = rememberZoomState()
    val viewModel: GalleryScreenViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    val currentImage by viewModel.currentImage().collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        state.size
    }, initialPage = state.indexOf(currentImage))
    val windowState = rememberWindowInsetsController()
    val showUI = rememberSaveable {
        mutableStateOf(true)
    }
    windowState.handle(showUI.value)

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                lowVelocityAnimationSpec = tween(
                    easing = LinearEasing,
                    durationMillis = 150
                )
            ),
            key = {
                if (state.isNotEmpty()) {
                    state[it.coerceIn(state.indices)].toString()
                } else "empty"
            },
            modifier = Modifier
                .zoomable(zoomState)
                .graphicsLayer(
                    scaleX = zoomState.scale,
                    scaleY = zoomState.scale
                )
        ) { index ->
            ZoomablePagerImage(
                imageUrl = state[index].imageUrl(),
                uiEnabled = showUI.value,
                setCurrentItem = {
                    viewModel.setCurrentItem(state[pagerState.currentPage])
                },
                onItemClick = {
                    showUI.value = !showUI.value
                },
            )
        }
        TopBarScreenImage(viewModel = viewModel, showUI = showUI.value)
        DownBarScreenImage(viewModel = viewModel, showUI = showUI.value)
    }
}
