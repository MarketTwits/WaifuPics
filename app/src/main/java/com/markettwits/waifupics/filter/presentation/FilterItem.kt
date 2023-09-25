package com.markettwits.waifupics.filter.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterItem(
    val id : Int,
    val title : String,
    val value : String,
    val checked : Boolean = false,
) : Parcelable{
    fun checked(isChecked : Boolean) = this.copy(checked = !isChecked)
}

