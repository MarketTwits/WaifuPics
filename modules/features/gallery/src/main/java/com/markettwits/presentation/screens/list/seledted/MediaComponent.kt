package com.markettwits.presentation.screens.list.seledted

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.markettwits.presentation.screens.ImageFavoriteUi
import run.nabla.gallerypicker.picker.GalleryPickerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.MediaComponent(
    media: ImageFavoriteUi,
    //selectionState: MutableState<Boolean>,
    selectionState: Boolean,
    imagePickerState: GalleryPickerState,
    selectedMedia: SnapshotStateList<ImageFavoriteUi>,
    onItemClick: (ImageFavoriteUi) -> Unit,
    onItemLongClick: (ImageFavoriteUi) -> Unit,
) {
    val isSelected = remember { mutableStateOf(false) }
    MediaImage(
        image = media,
        selectionState = selectionState,
        selectedMedia = selectedMedia,
        isSelected = isSelected,
        imagePickerState = imagePickerState,
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .animateItemPlacement()
            .combinedClickable(
                onClick = {
                    onItemClick(media)
//                    if (selectionState.value) {
//                        isSelected.value = !isSelected.value
//                    }
                },
                onLongClick = {
                    onItemLongClick(media)
//                    if (selectionState.value) {
//                        isSelected.value = !isSelected.value
//                    }
                },
            )
    )
}