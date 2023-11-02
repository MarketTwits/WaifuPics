package com.markettwits.presentation

import androidx.compose.foundation.layout.Box
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
import com.markettwits.core_ui.image.LocalImageLoader
import run.nabla.gallerypicker.picker.GalleryPickerState

sealed interface ImageFavoriteUi {
    @Composable
    fun GalleryItem(modifier: Modifier, state: GalleryPickerState)
    @Composable
    fun ImageItem() = Unit
    fun changeState(): ImageFavoriteUi
    fun imageUrl() : String

    data class Base(
        val id: Long,
        private val imageUrl: String,
        private val protected: Boolean
    ) : ImageFavoriteUi {
        @Composable
        override fun GalleryItem(modifier: Modifier,state: GalleryPickerState) {
            GalleryItemBase(modifier = modifier,isSelected = false, imageUrl = imageUrl, state = state)
        }
        @Composable
        override fun ImageItem() {
            ImageScreen(imageUrl = imageUrl)
        }
        override fun changeState() = Selected(id, imageUrl, protected)
        override fun imageUrl() = imageUrl
    }

    data class Selected(
        val id: Long,
        private val imageUrl: String,
        private val protected: Boolean
    ) : ImageFavoriteUi {


        @Composable
        override fun GalleryItem(modifier: Modifier,state: GalleryPickerState) {
            GalleryItemBase(modifier = modifier,isSelected = true, imageUrl = imageUrl, state = state)
        }

        @Composable
        override fun ImageItem() {
            ImageScreen(imageUrl = imageUrl)
        }

        override fun changeState() = Base(id, imageUrl, protected)
        override fun imageUrl() = imageUrl
    }

    data object Initial : ImageFavoriteUi {
        @Composable
        override fun GalleryItem(modifier: Modifier,state: GalleryPickerState) = Unit

        override fun changeState() = this
        override fun imageUrl() = ""
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GalleryItemBase(
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
        onClick = {  }
    ) {
        AsyncImage(
            modifier = modifier
                .heightIn(min = state.itemMinHeight.dp, max = state.itemMaxHeight.dp)
                .fillMaxSize(),
            model = LocalImageLoader.current.memoryWithDisk(imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
@Composable
private fun ImageScreen(
    modifier: Modifier = Modifier,
    imageUrl : String
){
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = "")
    }
}
