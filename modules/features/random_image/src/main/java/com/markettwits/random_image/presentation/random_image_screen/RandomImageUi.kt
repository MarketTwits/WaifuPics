package com.markettwits.random_image.presentation.random_image_screen

data class AuthorUi(
    val imageUrl : String,
    val title : String,
    val aliases : List<String>,
    val links : List<String>
)
data class ImageSourceUi(
    val sourceUrl : String,
    val ageRating : String
)
