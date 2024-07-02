package com.markettwits.data.media_info


data class ExifAttributes(
    val label: String,
    val fileSize : Long,
    val imageWidth : Int,
    val imageHeight : Int,
    val filePath : String,
    val createdDate : Long,
    val mimeType : String
)