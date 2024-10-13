package com.markettwits.cloud_datasource.presentation.random_image.model

import androidx.compose.runtime.Composable
import com.markettwits.cloud_datasource.presentation.random_image.components.image.loading.ImageLoading
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.components.image.suceess.ImageCard
import com.markettwits.cloud_datasource.presentation.random_image.components.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.cloud_datasource.presentation.random_image.components.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.cloud_datasource.presentation.random_image.components.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.cloud_datasource.presentation.filter.presentation.components.BottomSheetFilter
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.components.bottom_pannel.BottomPanel
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.components.image.fuckup.ImageFuckup
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.model.AuthorUi
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.model.ImageSourceUi

interface RandomImageUiState {

    @Composable
    fun Handle() = Unit

    object Initial : RandomImageUiState

    object Progress : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageLoading()
            ImageCardInfoLoading()
            BottomPanel()
        }
    }

    data class Error(private val message: String) : RandomImageUiState {
        @Composable
        override fun Handle() {
            ImageFuckup(message)
            BottomPanel()
            BottomSheetFilter()
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
            override fun Handle() {
                ImageCard(imageUrl, id)
                ImageInfoCardEmptyAuthor(imageData, colorPalette)
                BottomPanel()
                BottomSheetFilter()
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
            override fun Handle() {
                ImageCard(imageUrl, id)
                ImageInfoCardWitAuthor(author, imageData, colorPalette)
                BottomPanel()
                BottomSheetFilter()
            }
        }
    }
}