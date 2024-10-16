package com.markettwits.waifupics.gallery.items.components

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
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.gallery.items.components.empty_item.EmptyGalleryItem
import com.markettwits.waifupics.gallery.items.components.list_item.MediaComponent
import com.markettwits.waifupics.gallery.items.components.picker.SelectionSheet
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState
import com.markettwits.waifupics.gallery.items.viewmodel.GalleryViewModel


@Composable
fun GalleryScreen(onClickGoDetail : () -> Unit) {

    val viewModel: GalleryViewModel = ApplicationViewModel()
    val state by viewModel. state().collectAsState()
    val selected by viewModel.selectedState().collectAsState()
    val selectedMedia = viewModel.selectedPhotoState()

        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            if (state is ImageFavoriteUiState.Base) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(130.dp),
                ) {
                    items(state.items, key = { it.id }) { image ->
                        if (image is ImageFavoriteUi.Base) {
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
                                        onClickGoDetail()
                                    }
                                }
                            )
                        }
                    }
                }
            }
            if (state is ImageFavoriteUiState.Empty) {
                EmptyGalleryItem()
            }

            SelectionSheet(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .align(Alignment.BottomEnd),
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


