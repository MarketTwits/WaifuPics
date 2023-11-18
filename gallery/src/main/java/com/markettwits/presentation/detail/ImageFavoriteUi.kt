package com.markettwits.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.presentation.list.item.GalleryItemBase
import com.markettwits.presentation.list.item.GalleryItemProtected
import run.nabla.gallerypicker.picker.GalleryPickerState

sealed interface ImageFavoriteUi {
    @Composable
    fun GalleryItem(modifier: Modifier, state: GalleryPickerState)
    fun imageUrl(): String
    fun id(): Long
    fun created(): String
    fun protected() : Boolean
    fun toBase() : ImageFavoriteUi.Base
    abstract class Abstract(
        private val id: Long,
        private val imageUrl: String,
        private val created: String,
        private val protected: Boolean,
    ) : ImageFavoriteUi {
        override fun imageUrl() = imageUrl
        override fun id() = id
        override fun created() = created
        override fun protected() = protected
        override fun toBase(): ImageFavoriteUi.Base {
           return Base(id, imageUrl, created, false)
        }
    }

    data class Base(
        private val id: Long,
        private val imageUrl: String,
        private val created: String,
        private val protected: Boolean,
    ) : Abstract(id, imageUrl, created, protected) {
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {
            GalleryItemBase(
                modifier = modifier,
                isSelected = false,
                imageUrl = imageUrl,
                state = state
            )
        }
    }

    data class Protected(
        private val id: Long,
        private val imageUrl: String,
        private val created: String,
        private val protected: Boolean,
    ) : Abstract(id, imageUrl, created, protected) {
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {
            GalleryItemProtected(modifier = modifier,isSelected = false, state = state)
        }
    }

    data object Initial : ImageFavoriteUi {
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {

        }

        override fun imageUrl(): String = ""

        override fun id(): Long = 0L
        override fun created(): String {
            return ""
        }

        override fun protected() = false
        override fun toBase() = Base(0, "", "", false)
    }
}