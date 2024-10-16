package com.markettwits.waifupics.random.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.waifupics.random.components.bottom_pannel.BottomPanel
import com.markettwits.waifupics.random.components.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.random.components.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.waifupics.random.components.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading
import com.markettwits.waifupics.random.components.image_state.suceess.ImageCard
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.viewmodel.ImageViewModel


internal class RandomImageScreenComponent(private val viewModel: ImageViewModel) {

    @Composable
    fun Render() {
        val state = viewModel.state().collectAsState()

        when (val value = state.value) {
            is RandomImageState.Error -> {
                ImageFuckup(value.message)
                ConfigureBottomPanel()
                com.markettwits.waifupics.filter.components.AgeRatingFilter()
            }

            is RandomImageState.Initial -> {}

            is RandomImageState.Progress -> {
                ImageLoading()
                ImageCardInfoLoading()
                ConfigureBottomPanel()
            }

            is RandomImageState.Success -> {
                if (value is RandomImageState.Success.WithAuthor) {
                    ImageCard(
                        imageUrl = value.imageUrl,
                        id = value.id,
                        onChangeImageState = viewModel::obtainImageState
                    )
                    ImageInfoCardWitAuthor(
                        author = value.author,
                        imageData = value.imageData,
                        colorPalette = value.colorPalette
                    )
                    ConfigureBottomPanel()
                    com.markettwits.waifupics.filter.components.AgeRatingFilter()
                }
                if (value is RandomImageState.Success.EmptyAuthor) {
                    ImageCard(
                        imageUrl = value.imageUrl,
                        id = value.id,
                        onChangeImageState = viewModel::obtainImageState
                    )
                    ImageInfoCardEmptyAuthor(
                        imageData = value.imageData,
                        colorPalette = value.colorPalette
                    )
                    ConfigureBottomPanel()
                    com.markettwits.waifupics.filter.components.AgeRatingFilter()
                }
            }
        }

    }

    @Composable
    private fun ConfigureBottomPanel() {

        val panelState = viewModel.loadedImageState().collectAsState()
        val enabled = panelState.value is ImageState.Success
        val refresh = panelState.value is ImageState.Loading

        BottomPanel(
            isEnabled = enabled,
            isRefresh = refresh,
            onClickAddToFavorite = viewModel::onClickAddToFavorite,
            onClickShareImage = viewModel::onClickShareImage,
            onClickFetchRandomImage = viewModel::fetchRandomImage
        )
    }

}