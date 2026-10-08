package com.markettwits.waifupics.gallery.items.components.list_item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LazyGridItemScope.MediaComponent(
    media: ImageFavoriteUi,
    selectionState: Boolean,
    selectedMedia: List<ImageFavoriteUi>,
    onItemClick: (ImageFavoriteUi) -> Unit,
    onItemLongClick: (ImageFavoriteUi) -> Unit,
) {
    MediaImage(
        image = media,
        selectionState = selectionState,
        selectedMedia = selectedMedia,
        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
            .clip(androidx.compose.material3.MaterialTheme.shapes.medium)
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