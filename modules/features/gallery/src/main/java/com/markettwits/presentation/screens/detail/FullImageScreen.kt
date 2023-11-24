package com.markettwits.presentation.screens.detail

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.base_extensions.noRippleClickable
import com.markettwits.presentation.screens.detail.bottomBar.DownBarScreenImage
import com.markettwits.presentation.screens.detail.image.ZoomablePagerImage
import com.markettwits.presentation.screens.detail.topbar.TopBarScreenImage
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
    val windowInsetsController = rememberWindowInsetsController()
    val showUI = rememberSaveable{
        mutableStateOf(true)
    }

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
                .noRippleClickable {
                    //TODO change toolBar state
                    windowInsetsController.toggleSystemBars(showUI.value)
                }
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


@Composable
fun rememberWindowInsetsController(): WindowInsetsControllerCompat {
    val window = with(LocalContext.current as Activity) { return@with window }
    return remember { WindowCompat.getInsetsController(window, window.decorView) }
}

fun WindowInsetsControllerCompat.toggleSystemBars(show: Boolean) {
    if (show) show(WindowInsetsCompat.Type.systemBars())
    else hide(WindowInsetsCompat.Type.systemBars())
}



