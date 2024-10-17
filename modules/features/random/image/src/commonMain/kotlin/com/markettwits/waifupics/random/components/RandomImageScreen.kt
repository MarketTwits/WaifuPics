package com.markettwits.waifupics.random.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.filter.components.AgeRatingFilter
import com.markettwits.waifupics.random.components.bottom_pannel.ConfigureBottomPanel
import com.markettwits.waifupics.random.components.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.random.components.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.waifupics.random.components.image_info.image_card_info.success.ImageInfoCardWitAuthor
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading
import com.markettwits.waifupics.random.components.image_state.suceess.ImageCardContent
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.viewmodel.ImageViewModel

@Composable
fun RandomImageScreen(viewModel: ImageViewModel = ApplicationViewModel()) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = viewModel.state().collectAsState()

        val imageState = viewModel.loadedImageState().collectAsState()

        when (val value = state.value) {
            is RandomImageState.Error -> {
                ImageFuckup(message = value.message)
                ConfigureBottomPanel(
                    imageState = imageState.value,
                    imageId = 0,
                    onClickFetchRandomImage = viewModel::fetchRandomImage,
                    onClickShareImage = viewModel::onClickShareImage,
                    onClickAddToFavorite = viewModel::onClickAddToFavorite
                )
                AgeRatingFilter()
            }

            is RandomImageState.Initial -> {}

            is RandomImageState.Progress -> {
                ImageLoading()
                ImageCardInfoLoading()
                ConfigureBottomPanel(
                    imageState = imageState.value,
                    imageId = 0,
                    onClickFetchRandomImage = viewModel::fetchRandomImage,
                    onClickShareImage = viewModel::onClickShareImage,
                    onClickAddToFavorite = viewModel::onClickAddToFavorite
                )
                AgeRatingFilter()
            }

            is RandomImageState.Success -> {
                if (value is RandomImageState.Success.WithAuthor) {
                    ImageCardContent(
                        imageUrl = value.imageUrl,
                        id = value.id,
                        onChangeImageState = viewModel::obtainImageState
                    )
                    ImageInfoCardWitAuthor(
                        author = value.author,
                        imageData = value.imageData,
                        colorPalette = value.colorPalette
                    )
                    ConfigureBottomPanel(
                        imageState = imageState.value,
                        imageId = value.id,
                        onClickFetchRandomImage = viewModel::fetchRandomImage,
                        onClickShareImage = viewModel::onClickShareImage,
                        onClickAddToFavorite = viewModel::onClickAddToFavorite
                    )
                    AgeRatingFilter()
                }
                if (value is RandomImageState.Success.EmptyAuthor) {
                    ImageCardContent(
                        imageUrl = value.imageUrl,
                        id = value.id,
                        onChangeImageState = viewModel::obtainImageState
                    )
                    ImageInfoCardEmptyAuthor(
                        imageData = value.imageData,
                        colorPalette = value.colorPalette
                    )
                    ConfigureBottomPanel(
                        imageState = imageState.value,
                        imageId = value.id,
                        onClickFetchRandomImage = viewModel::fetchRandomImage,
                        onClickShareImage = viewModel::onClickShareImage,
                        onClickAddToFavorite = viewModel::onClickAddToFavorite
                    )
                    AgeRatingFilter()
                }
            }
        }
    }
}