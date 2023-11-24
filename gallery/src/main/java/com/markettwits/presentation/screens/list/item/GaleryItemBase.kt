package com.markettwits.presentation.screens.list.item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader
import run.nabla.gallerypicker.picker.GalleryPickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryItemBase(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    state: GalleryPickerState,
    imageUrl: String,
) {
    val shape = RoundedCornerShape(10.dp)
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = shape)
            .padding(2.dp),
        onClick = { }
    ) {
        AsyncImage(
            modifier = modifier
                .heightIn(min = state.itemMinHeight.dp, max = state.itemMaxHeight.dp)
                .fillMaxSize(),
            model = LocalImageLoader.current.single(imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
