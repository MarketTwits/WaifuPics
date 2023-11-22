package com.markettwits.random_image.presentation

import androidx.compose.runtime.Composable
import com.markettwits.filter.presentation.BottomSheetFilter
import com.markettwits.random_image.presentation.bottom_pannel.BottomPanel
import com.markettwits.random_image.presentation.image.loading.ImageLoading
import com.markettwits.random_image.presentation.image.suceess.ImageCard
import com.markettwits.random_image.presentation.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.random_image.presentation.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.view.main.ui.image.fuckup.ImageFuckup
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading.ImageCardInfoLoading

interface RandomImageUiState {
    @Composable
    fun Handle(firstRun: Boolean) = Unit

    object Initial : RandomImageUiState
    object Progress : RandomImageUiState {
        @Composable
        override fun Handle(firstRun: Boolean) {
            ImageLoading()
            ImageCardInfoLoading()
            BottomPanel()
        }
    }

    data class Error(private val message: String) : RandomImageUiState {
        @Composable
        override fun Handle(firstRun: Boolean) {
            ImageFuckup()
            BottomPanel()
            BottomSheetFilter(firstRun)
        }
    }

    interface Success : RandomImageUiState {

        data class EmptyAuthor(
            private val id: Int,
            private val imageUrl: String,
            private val colorPalette: List<List<Int>>,
            private val imageData: ImageSourceUi,
        ) : Success {
            @Composable
            override fun Handle(firstRun: Boolean) {
                ImageCard(imageUrl, id)
                ImageInfoCardEmptyAuthor(imageData, colorPalette)
                BottomPanel(imageUrl)
                BottomSheetFilter(firstRun)
            }

        }


        data class WithAuthor(
            private val id: Int,
            private val imageUrl: String,
            private val colorPalette: List<List<Int>>,
            private val imageData: ImageSourceUi,
            private val author: AuthorUi,
        ) : Success {
            @Composable
            override fun Handle(firstRun: Boolean) {
                ImageCard(imageUrl, id)
                ImageInfoCardWitAuthor(author, imageData, colorPalette)
                BottomPanel(imageUrl)
                BottomSheetFilter(firstRun)
            }
        }
    }
}