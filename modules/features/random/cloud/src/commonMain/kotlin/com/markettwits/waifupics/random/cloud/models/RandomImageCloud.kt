package com.markettwits.waifupics.random.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomImageCloud(
    @SerialName("items")
    val items : List<RandomImageItemCloud>
)