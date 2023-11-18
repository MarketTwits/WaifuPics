package com.markettwits.random_image.data

import com.markettwits.random_image.data.net.models.RandomImageItemCloud
import com.markettwits.random_image.ui.AuthorUi
import com.markettwits.random_image.ui.ImageSourceUi
import com.markettwits.random_image.ui.RandomImageUiState

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