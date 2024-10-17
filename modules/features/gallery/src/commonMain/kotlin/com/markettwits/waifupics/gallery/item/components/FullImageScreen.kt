package com.markettwits.waifupics.gallery.item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.gallery.item.components.bottomBar.BottomBarScreenImage
import com.markettwits.waifupics.gallery.item.components.image.ZoomablePagerImage
import com.markettwits.waifupics.gallery.item.components.topbar.TopBarScreenImage
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.items.components.animations.rememberPagerFlingBehavior


@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
    onClickPop: () -> Unit,
) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()

    val currentImage by viewModel.currentImage().collectAsState()

    val state by viewModel.state().collectAsState()

    val labels by viewModel.labels.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        state.items.size
    }, initialPage = state.items.indexOf(currentImage))
    val showUI = rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(labels) {
        when (labels) {
            GalleryScreenViewModel.Labels.Empty -> {}
            GalleryScreenViewModel.Labels.GoBack -> onClickPop()
        }
    }


    LaunchedEffect(pagerState, state) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.setCurrentItem(page)
        }
    }
    Box(
        modifier = modifier
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
                },
            )
        }
        TopBarScreenImage(
            showUI = showUI.value,
            createdDate = currentImage.created,
            onClickGoBack = onClickPop
        )
        BottomBarScreenImage(
            showUI = showUI.value,
            onClickDeleteImage = {
                viewModel.onClickDelete()
            },
            onClickShareImage = {
                viewModel.onClickShareImage()
            }
        )
    }
}

