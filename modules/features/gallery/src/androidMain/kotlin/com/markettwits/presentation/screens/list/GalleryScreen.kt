package com.markettwits.presentation.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.presentation.screens.ImageFavoriteUi
import com.markettwits.presentation.screens.ImageFavoriteUiState
import com.markettwits.presentation.screens.detail.window_controller.rememberWindowInsetsController
import com.markettwits.presentation.screens.detail.window_controller.toggleSystemBars
import com.markettwits.presentation.screens.list.empty_item.EmptyGalleryItem
import com.markettwits.presentation.screens.list.list_item.MediaComponent
import com.markettwits.presentation.screens.list.picker.SelectionSheet


@Composable
fun GalleryScreen() {

    val viewModel: GalleryViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    val selected by viewModel.selectedState().collectAsState()
    val selectedMedia = viewModel.selectedPhotoState()
    val view = LocalView.current
    rememberWindowInsetsController().toggleSystemBars(true)

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (state is ImageFavoriteUiState.Base){
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
            ) {
                items(state.items, key = { it.id }) { image ->
                    if (image is ImageFavoriteUi.Base){
                        MediaComponent(
                            media = image,
                            selectionState = selected,
                            selectedMedia = selectedMedia,
                            onItemLongClick = {
                                viewModel.selection(state.items.indexOf(it))
                                viewModel.changeSelectedState()
                            },
                            onItemClick = {
                                if (selected) {
                                    viewModel.selection(state.items.indexOf(it))
                                } else {
                                    viewModel.toDetail(image)
                                }
                            }
                        )
                    }
                }
            }
        }
        if (state is ImageFavoriteUiState.Empty){
            EmptyGalleryItem()
        }

        SelectionSheet(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(WindowInsets.navigationBars),
            selectedMedia = selectedMedia,
            selectionState = selected,
            clearSelection = {
                viewModel.changeSelectedState()
            },
            shareImages = {
                viewModel.shareImages()
            },
            deleteImages = {
                viewModel.delete()
            }
        )
    }

}

