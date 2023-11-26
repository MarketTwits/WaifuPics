package com.markettwits.presentation.screens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.presentation.screens.list.item.GalleryItemBase
import com.markettwits.presentation.screens.list.item.GalleryItemProtected
import kotlinx.parcelize.Parcelize
import run.nabla.gallerypicker.picker.GalleryPickerState
@Parcelize
sealed interface ImageFavoriteUi : Parcelable {
    @Composable
    fun GalleryItem(modifier: Modifier, state: GalleryPickerState)
    fun imageUrl(): String
    fun id(): Long
    fun created(): String
    fun protected() : Boolean
    fun toBase() : Base


    @Parcelize
    data class Base(
        private val id: Long,
        private val imageUrl: String,
        private val created: String,
        private val protected: Boolean,
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
        override fun protected() = protected
        override fun toBase(): Base {
            return Base(id, imageUrl, created, false)
        }
    }
    @Parcelize
    data class Protected(
        private val id: Long,
        private val imageUrl: String,
        private val created: String,
        private val protected: Boolean,
    ) : ImageFavoriteUi {
        @Composable
        override fun GalleryItem(modifier: Modifier, state: GalleryPickerState) {
            GalleryItemProtected(modifier = modifier,isSelected = false, state = state)
        }
        override fun imageUrl() = imageUrl
        override fun id() = id
        override fun created() = created
        override fun protected() = protected
        override fun toBase(): Base {
            return Base(id, imageUrl, created, false)
        }
    }
    @Parcelize
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