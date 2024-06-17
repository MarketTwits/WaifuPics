package com.markettwits.presentation.screens.detail

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.presentation.animations.rememberPagerFlingBehavior
import com.markettwits.presentation.screens.detail.bottomBar.BottomBarScreenImage
import com.markettwits.presentation.screens.detail.image.ZoomablePagerImage
import com.markettwits.presentation.screens.detail.topbar.TopBarScreenImage
import com.markettwits.presentation.screens.detail.window_controller.rememberWindowInsetsController
import com.markettwits.presentation.screens.detail.window_controller.toggleSystemBars

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
) {
    val viewModel: GalleryScreenViewModel.Base = ApplicationViewModel()
    val currentImage by viewModel.currentImage().collectAsState()
    val state by viewModel.state().collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        state.items.size
    }, initialPage = state.items.indexOf(currentImage))
    val windowState = rememberWindowInsetsController()
    val showUI = rememberSaveable {
        mutableStateOf(true)
    }


    LaunchedEffect(pagerState, state) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.setCurrentItem(page)
        }
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
                if (state.items.isNotEmpty()) {
                    state.items[it.coerceIn(state.items.indices)].toString()
                } else "empty"
            }
        ) { index ->
            ZoomablePagerImage(
                imageUrl = state.items[index].imageUrl,
                uiEnabled = showUI.value,
                setCurrentItem = {},
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

