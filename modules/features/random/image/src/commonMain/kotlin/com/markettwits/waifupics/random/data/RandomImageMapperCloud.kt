package com.markettwits.waifupics.random.data

import com.markettwits.waifupics.random.cloud.models.RandomImageItemCloud
import com.markettwits.waifupics.random.cloud.proxyImageUrl
import com.markettwits.waifupics.random.model.ImageSourceUi
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.result.NetworkResult

class RandomImageMapperCloud : NetworkResult.Mapper<RandomImageItemCloud, RandomImageState> {

    override fun map(item: RandomImageItemCloud): RandomImageState =

        RandomImageState.Success(
            id = item.id,
            imageUrl = item.url.proxyImageUrl(),
            colorPalette = item.color_palette,
            imageData = ImageSourceUi(item.source_url ?: "", item.rating),
        )

    override fun map(errorMessage: String, code: Int): RandomImageState =
        RandomImageState.Error(errorMessage)
}