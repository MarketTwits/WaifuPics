package com.markettwits.waifupics.view.main.ui

import android.util.Log
import androidx.compose.runtime.Composable
import com.markettwits.waifupics.view.filter.BottomSheetFilter
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanel
import com.markettwits.waifupics.view.main.ui.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.ui.image.suceess.ImageCard
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardWithUploader

interface RandomImageUiState {
    @Composable
    fun Handle() = Unit

    object Initial : RandomImageUiState {}

    object Progress : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageLoading()
            ImageCardInfoLoading()
            BottomPanel(isLoading = true)
        }
    }

    data class Error(val message: String) : RandomImageUiState {
        @Composable
        override fun Handle() {
            Log.d("mt05", message)
        }
    }

    data class SuccessWithOwner(
        val imageUrl: String,
        val collorPallente: List<String>,
        val imageData: ImageSourceUi,
        val owner: UploaderUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageCard(imageUrl)
            ImageInfoCardWithUploader(owner, imageData, collorPallente)
            BottomPanel(isLoading = false)
            BottomSheetFilter()
        }
    }

    data class SuccessEmptyAuthor(
        val imageUrl: String,
        val collorPallente: List<String>,
        val imageData: ImageSourceUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageCard(imageUrl)
            ImageInfoCardEmptyAuthor(imageData, collorPallente)
            BottomPanel(isLoading = false)
            BottomSheetFilter()
        }
    }

    data class SuccessWithAuthor(
        val imageUrl: String,
        val collorPallente: List<String>,
        val imageData: ImageSourceUi,
        val author: AuthorUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageCard(imageUrl)
            ImageInfoCardWitAuthor(
                author = author,
                imageData = imageData,
                colorPalette = collorPallente
            )
            BottomPanel(isLoading = false)
            BottomSheetFilter()
        }
    }
}