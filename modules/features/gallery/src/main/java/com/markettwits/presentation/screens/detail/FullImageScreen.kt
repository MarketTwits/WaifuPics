package com.markettwits.presentation.screens.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.local_di.ApplicationViewModel
import com.markettwits.presentation.animations.rememberPagerFlingBehavior
import com.markettwits.presentation.screens.detail.bottomBar.BottomBarScreenImage
import com.markettwits.presentation.screens.detail.image.ZoomablePagerImage
import com.markettwits.presentation.screens.detail.topbar.TopBarScreenImage
import com.markettwits.presentation.screens.detail.window_controller.rememberWindowInsetsController
import com.markettwits.presentation.screens.detail.window_controller.toggleSystemBars
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
    val currentImage by viewModel.currentImage().collectAsState()
    val state by viewModel.state().collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        state.size
    }, initialPage = state.indexOf(currentImage))
    val windowState = rememberWindowInsetsController()
    val showUI = rememberSaveable {
        mutableStateOf(true)
    }
    if (state.isEmpty()) {
        viewModel.pop()
    }
    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            flingBehavior = rememberPagerFlingBehavior(pagerState = pagerState),
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
                imageUrl = state[index].imageUrl,
                uiEnabled = showUI.value,
                setCurrentItem = {
                    //TODO crash when delete last item it list
                    val item = state[pagerState.currentPage]
                    viewModel.setCurrentItem(item)
                    Log.d("mt06", index.toString())
                    Log.d("mt06", item.toString())
                },
                onItemClick = {
                    showUI.value = !showUI.value
                    windowState.toggleSystemBars(showUI.value)
                },
            )
        }
        TopBarScreenImage(viewModel = viewModel, showUI = showUI.value)
        BottomBarScreenImage(viewModel = viewModel, showUI = showUI.value)
    }
}
