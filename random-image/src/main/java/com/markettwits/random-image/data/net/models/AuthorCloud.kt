package com.markettwits.waifupics.view.main.data.net.models

data class AuthorCloud(
    val data : AuthorDataCloud
)
data class AuthorDataCloud(
    val type: String,
    val id : String,
    val attributes : AuthorAttributesCloud
)
data class AuthorAttributesCloud(
    val name : String,
    val aliases : List<String>,
    val imageUrl : String,
    val officialLinks : List<String>,
)
