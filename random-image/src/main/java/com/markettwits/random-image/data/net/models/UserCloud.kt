package com.markettwits.waifupics.view.main.data.net.models

data class UserCloud(
    val data : UserCloudData
)
data class UserCloudData(
    val type : String,
    val id : String,
    val attributes : AttributesUserCloud
)
data class AttributesUserCloud(
    val username : String,
    val avatarImage : String
)
