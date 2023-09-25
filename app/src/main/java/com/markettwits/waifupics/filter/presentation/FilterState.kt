package com.markettwits.waifupics.filter.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
) : Parcelable{
    fun toggle() = this.copy(isOpened = !isOpened)
    fun couldBeChecked(item: FilterItem) =
        filter.filter { it.checked }.size == 1 && item.checked
}