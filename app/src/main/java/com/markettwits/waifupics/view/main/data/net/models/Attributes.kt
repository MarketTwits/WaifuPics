package com.markettwits.waifupics.view.main.data.net.models

data class Attributes(
    val ageRating: String,
    val colors: Colors,
    val dimens: Dimens,
    val `file`: String,
    val isOriginal: Boolean,
    val metadata: Metadata,
    val source: Source,
    val timestamps: Timestamps,
    val title: String,
    val verificationStatus: String
)