package com.markettwits.waifupics.random.model

import androidx.compose.runtime.Stable

@Stable
data class AuthorUi(
    val imageUrl : String,
    val title : String,
    val aliases : List<String>,
    val links : List<String>
)

@Stable
data class ImageSourceUi(
    val sourceUrl : String,
    val ageRating : String
)
