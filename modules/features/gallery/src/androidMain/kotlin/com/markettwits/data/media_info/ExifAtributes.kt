package com.markettwits.data.media_info


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExifAttributes(
    val label: String,
    val fileSize : Long,
    val imageWidth : Int,
    val imageHeight : Int,
    val filePath : String,
    val createdDate : Long,
    val mimeType : String
) : Parcelable