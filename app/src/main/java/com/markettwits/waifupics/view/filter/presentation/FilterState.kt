package com.markettwits.waifupics.view.filter.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
) : Parcelable{
    fun toggle() = this.copy(isOpened = !isOpened)

}