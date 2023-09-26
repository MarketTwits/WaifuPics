package com.markettwits.`random-image`.ui

import androidx.compose.runtime.Composable
import com.markettwits.filter.presentation.BottomSheetFilter
import com.markettwits.`random-image`.ui.bottom_pannel.BottomPanel
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
    interface Success : RandomImageUiState{
        data class WithOwner(
            private val imageUrl: String,
            private val colorPalette: List<String>,
            private val imageData: ImageSourceUi,
            private val owner: UploaderUi
        ) : Success{
            @Composable
            override fun Handle(firstRun: Boolean) {
                ImageCard(imageUrl)
                ImageInfoCardWithUploader(owner, imageData, colorPalette)
                BottomPanel(BottomPanelUiState.Success, imageUrl, imageData.ageRating)
                BottomSheetFilter(firstRun)
            }
        }
        data class EmptyAuthor(
            private val imageUrl: String,
            private val colorPalette: List<String>,
            private val imageData: ImageSourceUi
        ) : Success{
            @Composable
            override fun Handle(firstRun : Boolean) {
                ImageCard(imageUrl)
                ImageInfoCardEmptyAuthor(imageData, colorPalette)
                BottomPanel(BottomPanelUiState.Success, imageUrl, imageData.ageRating)
                BottomSheetFilter(firstRun)
            }
        }
        data class WithAuthor(
            private val imageUrl: String,
            private val colorPalette: List<String>,
            private val imageData: ImageSourceUi,
            private val author: AuthorUi
        ) : Success{
            @Composable
            override fun Handle(firstRun : Boolean) {
                ImageCard(imageUrl)
                ImageInfoCardWitAuthor(author, imageData,colorPalette)
                BottomPanel(BottomPanelUiState.Success, imageUrl,imageData.ageRating)
                BottomSheetFilter(firstRun)
            }
        }
    }
}