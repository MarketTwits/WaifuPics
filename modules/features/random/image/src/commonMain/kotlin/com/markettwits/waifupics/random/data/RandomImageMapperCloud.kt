package com.markettwits.waifupics.random.data

import com.markettwits.waifupics.random.cloud.models.RandomImageItemCloud
import com.markettwits.waifupics.random.model.ImageSourceUi
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.result.NetworkResult

class RandomImageMapperCloud : NetworkResult.Mapper<RandomImageItemCloud, RandomImageState> {

    override fun map(item: RandomImageItemCloud): RandomImageState.Success =

        RandomImageState.Success(
            id = item.id,
            imageUrl = item.url,
            colorPalette = item.colorPalette,
            imageData = ImageSourceUi(
                sourceUrl = item.sourceUrl ?: "",
                ageRating = item.rating,
                artistName = item.artistName?.trim()?.takeIf(String::isNotEmpty),
                tags = item.tags.filter(String::isNotBlank).distinct()
            ),
        )

    override fun map(errorMessage: String, code: Int): RandomImageState =
        RandomImageState.Error(errorMessage)
}
