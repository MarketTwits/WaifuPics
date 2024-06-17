package com.markettwits.random_image.data.mapper

import com.markettwits.random_image.data.network.models.RandomImageItemCloud
import com.markettwits.random_image.presentation.screen.AuthorUi
import com.markettwits.random_image.presentation.screen.ImageSourceUi
import com.markettwits.random_image.presentation.screen.RandomImageUiState

interface RandomImageUiMapper {
    fun map(cloud: RandomImageItemCloud): RandomImageUiState

    class Base : RandomImageUiMapper {
        override fun map(
            cloud: RandomImageItemCloud,
        ): RandomImageUiState {
            val item = if (cloud.artist != null)
                RandomImageUiState.Success.WithAuthor(
                    id = cloud.id,
                    imageUrl = cloud.image_url,
                    colorPalette = cloud.color_palette,
                    imageData = ImageSourceUi(cloud.source ?: "", cloud.rating),
                    author = AuthorUi(
                        imageUrl = cloud.artist.image_url ?: "",
                        title = cloud.artist.name,
                        aliases = cloud.artist.aliases,
                        links = cloud.artist.links
                    )
                )
            else {
                RandomImageUiState.Success.EmptyAuthor(
                    id = cloud.id,
                    imageUrl = cloud.image_url,
                    colorPalette = cloud.color_palette,
                    imageData = ImageSourceUi(cloud.source ?: "", cloud.rating),
                )
            }
            return item
        }
    }
}