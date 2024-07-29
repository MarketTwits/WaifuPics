package com.markettwits.cloud_datasource.data

import com.markettwits.cloud_datasource.data.cloud.NetworkResult
import com.markettwits.cloud_datasource.data.network.models.RandomImageItemCloud
import com.markettwits.cloud_datasource.presentation.random_image.model.AuthorUi
import com.markettwits.cloud_datasource.presentation.random_image.model.ImageSourceUi
import com.markettwits.cloud_datasource.presentation.random_image.model.RandomImageUiState

class RandomImageMapperCloud : NetworkResult.Mapper<RandomImageItemCloud, RandomImageUiState> {

    override fun map(item: RandomImageItemCloud): RandomImageUiState {
        val result = if (item.artist != null)
            RandomImageUiState.Success.WithAuthor(
                id = item.id,
                imageUrl = item.image_url,
                colorPalette = item.color_palette,
                imageData = ImageSourceUi(item.source ?: "", item.rating),
                author = AuthorUi(
                    imageUrl = item.artist?.image_url ?: "",
                    title = item.artist?.name ?: "",
                    aliases = item.artist?.aliases ?: emptyList(),
                    links = item.artist?.links ?: emptyList()
                )
            )
        else {
            RandomImageUiState.Success.EmptyAuthor(
                id = item.id,
                imageUrl = item.image_url,
                colorPalette = item.color_palette,
                imageData = ImageSourceUi(item.source ?: "", item.rating),
            )
        }
        return result
    }

    override fun map(errorMessage: String, code: Int): RandomImageUiState = RandomImageUiState.Error(errorMessage)
}