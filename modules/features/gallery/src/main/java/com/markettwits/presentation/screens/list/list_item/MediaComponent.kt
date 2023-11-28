package com.markettwits.presentation.screens.list.list_item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.markettwits.core_ui.components.Shapes
import com.markettwits.presentation.screens.ImageFavoriteUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.MediaComponent(
    media: ImageFavoriteUi,
    selectionState: Boolean,
    selectedMedia: List<ImageFavoriteUi>,
    onItemClick: (ImageFavoriteUi) -> Unit,
    onItemLongClick: (ImageFavoriteUi) -> Unit,
) {
    val isSelected = remember { mutableStateOf(false) }
    MediaImage(
        image = media,
        selectionState = selectionState,
        selectedMedia = selectedMedia,
        isSelected = isSelected,
        modifier = Modifier
            .clip(Shapes.extraSmall)
            .animateItemPlacement()
            .combinedClickable(
                onClick = {
                    onItemClick(media)
                },
                onLongClick = {
                    onItemLongClick(media)
                },
            )
    )
}