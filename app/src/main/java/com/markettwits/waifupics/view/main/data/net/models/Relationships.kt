package com.markettwits.waifupics.view.main.data.net.models

data class Relationships(
    val artist: Artist,
    val categories: Categories,
    val characters: Characters,
    val likedBy: LikedBy,
    val uploader: Uploader
)