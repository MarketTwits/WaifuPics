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
                    cloud.id,
                    cloud.image_url,
                    cloud.color_palette,
                    ImageSourceUi(cloud.source ?: "", cloud.rating),
                    AuthorUi(
                        imageUrl = cloud.artist.image_url ?: "",
                        cloud.artist.name,
                        cloud.artist.aliases,
                        cloud.artist.links
                    )
                )
            else {
                RandomImageUiState.Success.EmptyAuthor(
                    cloud.id,
                    cloud.image_url,
                    cloud.color_palette,
                    ImageSourceUi(cloud.source ?: "", cloud.rating),
                )
            }
            return item
        }
    }
}