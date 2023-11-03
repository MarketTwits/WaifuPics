package com.markettwits.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import run.nabla.gallerypicker.picker.GalleryPickerState

@OptIn(ExperimentalMaterialApi::class)
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
            model = loadImageBitmap(imagePath = imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

private fun loadImageBitmap(imagePath: String): Bitmap? {
    return try {
        BitmapFactory.decodeFile(imagePath)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
