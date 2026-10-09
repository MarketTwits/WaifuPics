package com.markettwits.waifupics.gallery.items.components.list_item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LazyGridItemScope.MediaComponent(
    media: ImageFavoriteUi,
    modifier: Modifier = Modifier,
    selectionState: Boolean,
    selectedMedia: List<ImageFavoriteUi>,
    onItemClick: (ImageFavoriteUi) -> Unit,
    onItemLongClick: (ImageFavoriteUi) -> Unit,
    imageTransition: GalleryImageTransition? = null,
) {
    MediaImage(
        image = media,
        selectionState = selectionState,
        selectedMedia = selectedMedia,
        imageTransition = imageTransition,
        modifier = modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
            .clip(androidx.compose.material3.MaterialTheme.shapes.medium)
            .semantics { onLongClick { onItemLongClick(media); true } }
            .combinedClickable(
                onClick = {
                    onItemClick(media)
                },
                onLongClick = null,
            )
    )
}
