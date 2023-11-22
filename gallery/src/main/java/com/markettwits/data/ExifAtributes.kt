package com.markettwits.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExifAttributes(
    var manufacturerName: String? = null,
    var modelName: String? = null,
    var apertureValue: Double? = null,
    var focalLength: Double? = null,
    var isoValue: Int? = null,
    var imageDescription: String? = null,
    var gpsLatLong: String? = null,
) : Parcelable