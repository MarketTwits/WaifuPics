package com.markettwits.waifupics.gallery.items.components.list_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.markettwits.waifupics.gallery.common.rememberGalleryImageRequest
import com.markettwits.waifupics.gallery.items.components.picker.CheckBox
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition

@Composable
internal fun MediaImage(image: ImageFavoriteUi, selectionState: Boolean,
    selectedMedia: List<ImageFavoriteUi>, modifier: Modifier = Modifier,
    imageTransition: GalleryImageTransition? = null) {
    val selected = selectionState && selectedMedia.any { it.id == image.id }
    val inset by animateDpAsState(if (selected) 8.dp else 0.dp, label = "Selection inset")
    val borderColor by animateColorAsState(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "Selection border")
    Box(modifier.aspectRatio(0.8f).semantics { this.selected = selected }) {
        val imageModifier = Modifier.fillMaxSize().padding(inset)
        AsyncImage(
            model = rememberGalleryImageRequest(image), contentDescription = null,
            modifier = (imageTransition?.image(imageModifier, image.id) ?: imageModifier).clip(MaterialTheme.shapes.medium)
                .border(if (selected) 2.dp else 0.dp, borderColor, MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        AnimatedVisibility(visible = selectionState) {
            Box(Modifier.padding(8.dp)) { CheckBox(isChecked = selected) }
        }
    }
}
