package com.markettwits.random_image.data.mapper

import com.markettwits.random_image.data.cloud.NetworkResult
import com.markettwits.random_image.data.network.models.RandomImageItemCloud
import com.markettwits.random_image.presentation.screen.AuthorUi
import com.markettwits.random_image.presentation.screen.ImageSourceUi
import com.markettwits.random_image.presentation.screen.RandomImageUiState

class RandomImageMapperCloud : NetworkResult.Mapper<RandomImageItemCloud, RandomImageUiState> {
    override fun map(item: RandomImageItemCloud): RandomImageUiState {
        val result = if (item.artist != null)
            RandomImageUiState.Success.WithAuthor(
                item.id,
                item.image_url,
                item.color_palette,
                ImageSourceUi(item.source ?: "", item.rating),
                AuthorUi(
                    imageUrl = item.artist.image_url ?: "",
                    item.artist.name,
                    item.artist.aliases,
                    item.artist.links
                )
            )
        else {
            RandomImageUiState.Success.EmptyAuthor(
                item.id,
                item.image_url,
                item.color_palette,
                ImageSourceUi(item.source ?: "", item.rating),
            )
        }
        return result
    }

    override fun map(errorMessage: String, code: Int): RandomImageUiState {
        return RandomImageUiState.Error(errorMessage)
    }
}