package com.markettwits.`random-image`.ui

data class AuthorUi(
    val imageUrl : String,
    val title : String,
    val aliases : List<String>,
    val links : List<String>
)
data class UploaderUi(
    val userName : String,
    val avatarUrl : String
)
data class ImageSourceUi(
    val source : String,
    val sourceUrl : String,
    val ageRating : String
)
