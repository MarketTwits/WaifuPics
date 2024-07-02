package com.markettwits.waifupics.filter.presentation

import com.markettwits.cloud_datasource.presentation.components.filter.presentation.FilterItem


data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
) {
    fun toggle() = this.copy(isOpened = !isOpened)
    fun couldBeChecked(item: FilterItem) =
        filter.filter { it.checked }.size == 1 && item.checked
}