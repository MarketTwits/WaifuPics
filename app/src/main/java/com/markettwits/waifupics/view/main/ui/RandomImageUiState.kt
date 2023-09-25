package com.markettwits.waifupics.view.main.ui

import androidx.compose.runtime.Composable
import com.markettwits.waifupics.filter.presentation.BottomSheetFilter
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanel
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanelUiState
import com.markettwits.waifupics.view.main.ui.image.fuckup.ImageFuckup
import com.markettwits.waifupics.view.main.ui.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.ui.image.suceess.ImageCard
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCardWithUploader

interface RandomImageUiState {
    @Composable
    fun Handle(firstRun : Boolean) = Unit

    object Initial : RandomImageUiState

    object Progress : RandomImageUiState {
        @Composable
        override fun Handle(firstRun : Boolean) {
            ImageLoading()
            ImageCardInfoLoading()
            BottomPanel(BottomPanelUiState.Loading)
        }
    }

    data class Error(private val message: String) : RandomImageUiState {
        @Composable
        override fun Handle(firstRun : Boolean) {
            ImageFuckup()
            BottomPanel(BottomPanelUiState.Error)
            BottomSheetFilter(firstRun)
        }
    }

    data class SuccessWithOwner(
        private val imageUrl: String,
        private val collorPallente: List<String>,
        private val imageData: ImageSourceUi,
        private val owner: UploaderUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle(firstRun : Boolean) {
            ImageCard(imageUrl)
            ImageInfoCardWithUploader(owner, imageData, collorPallente)
            BottomPanel(BottomPanelUiState.Success)
            BottomSheetFilter(firstRun)
        }
    }

    data class SuccessEmptyAuthor(
        val imageUrl: String,
        val collorPallente: List<String>,
        val imageData: ImageSourceUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle(firstRun : Boolean) {
            ImageCard(imageUrl)
            ImageInfoCardEmptyAuthor(imageData, collorPallente)
            BottomPanel(BottomPanelUiState.Success)
            BottomSheetFilter(firstRun)
        }
    }

    data class SuccessWithAuthor(
        val imageUrl: String,
        val collorPallente: List<String>,
        val imageData: ImageSourceUi,
        val author: AuthorUi
    ) : RandomImageUiState {
        @Composable
        override fun Handle(firstRun : Boolean) {
            ImageCard(imageUrl)
            ImageInfoCardWitAuthor(
                author = author,
                imageData = imageData,
                colorPalette = collorPallente
            )
            BottomPanel(BottomPanelUiState.Success)
            BottomSheetFilter(firstRun)
        }
    }
}