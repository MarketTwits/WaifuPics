package com.markettwits.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.presentation.GalleryItemBase
import run.nabla.gallerypicker.picker.GalleryPickerState

sealed interface ImageFavoriteUi {
    @Composable
    fun GalleryItem(modifier: Modifier, state: GalleryPickerState)
    fun imageUrl(): String
    fun id(): Long
    fun created() : String

    data class Base(
        val id: Long,
        val imageUrl: String,
        val created : String,
        val protected: Boolean
    ) : ImageFavoriteUi {
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {
            GalleryItemBase(
                modifier = modifier,
                isSelected = false,
                imageUrl = imageUrl,
                state = state
            )
        }

        override fun imageUrl() = imageUrl
        override fun id() = id
        override fun created() = created
    }

//    data class Selected(
//        val id: Long,
//        private val imageUrl: String,
//
//        private val protected: Boolean
//    ) : ImageFavoriteUi {
//
//
//        @Composable
//        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {
//            GalleryItemBase(
//                modifier = modifier,
//                isSelected = true,
//                imageUrl = imageUrl,
//                state = state
//            )
//        }
//
//        override fun imageUrl() = imageUrl
//        override fun id() = id
//        override fun created() = created()
//    }
    data object Initial : ImageFavoriteUi{
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {

        }

        override fun imageUrl(): String = ""

        override fun id(): Long = 0L
    override fun created(): String {
        return ""
    }
}
}