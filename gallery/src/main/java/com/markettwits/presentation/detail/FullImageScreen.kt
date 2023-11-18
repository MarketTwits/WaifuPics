package com.markettwits.presentation.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.base_extensions.noRippleClickable
import com.markettwits.presentation.detail.bottomBar.DownBarScreenImage
import com.markettwits.presentation.detail.image.ImageContent
import com.markettwits.presentation.detail.topbar.TopBarScreenImage
import kotlinx.coroutines.launch
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
    val handlePanel by viewModel.imagePanelState().collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        state.size
    }, initialPage = state.indexOf(currentImage))
    //viewModel.initScreen()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .noRippleClickable { viewModel.activePanel() },
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
           // if (handlePanel.visible())
                TopBarScreenImage(viewModel)
        }, bottomBar = {
           // if (handlePanel.visible())
                DownBarScreenImage(viewModel = viewModel)
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .zoomable(zoomState)
                        .graphicsLayer(
                            scaleX = zoomState.scale,
                            scaleY = zoomState.scale
                        )
                ) { index ->
                    ImageContent(
                        imageUrl = state[index].imageUrl(),
                        paddingValues = it,
                    ){
                        viewModel.setCurrentItem(state[pagerState.currentPage])
                    }
                }
            }
        }
}



