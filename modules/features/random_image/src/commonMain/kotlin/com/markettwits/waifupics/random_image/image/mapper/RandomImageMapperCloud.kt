package com.markettwits.waifupics.random_image.image.mapper

import com.markettwits.cloud_datasource.data.cloud.NetworkResult
import com.markettwits.cloud_datasource.data.network.models.RandomImageItemCloud
import com.markettwits.cloud_datasource.data.network.models.proxyImageUrl
import com.markettwits.waifupics.random_image.image.model.AuthorUi
import com.markettwits.waifupics.random_image.image.model.ImageSourceUi
import com.markettwits.waifupics.random_image.image.model.RandomImageState

class RandomImageMapperCloud : NetworkResult.Mapper<RandomImageItemCloud, RandomImageState> {

    override fun map(item: RandomImageItemCloud): RandomImageState {
        val result = if (item.artist != null)
            RandomImageState.Success.WithAuthor(
                id = item.id,
                imageUrl = item.image_url,
                colorPalette = item.color_palette,
                imageData = ImageSourceUi(item.source ?: "", item.rating),
                author = AuthorUi(
                    imageUrl = (item.artist?.image_url ?: "").proxyImageUrl(),
                    title = item.artist?.name ?: "",
                    aliases = item.artist?.aliases ?: emptyList(),
                    links = item.artist?.links ?: emptyList()
                )
            )
        else {
            RandomImageState.Success.EmptyAuthor(
                id = item.id,
                imageUrl = item.image_url.proxyImageUrl(),
                colorPalette = item.color_palette,
                imageData = ImageSourceUi(item.source ?: "", item.rating),
            )
        }
        return result
    }

    override fun map(errorMessage: String, code: Int): RandomImageState =
        RandomImageState.Error(errorMessage)
}