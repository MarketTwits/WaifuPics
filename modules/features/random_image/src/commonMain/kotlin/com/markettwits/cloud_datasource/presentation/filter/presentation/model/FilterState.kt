package com.markettwits.cloud_datasource.presentation.filter.presentation.model


data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
) {
    fun toggle() = this.copy(isOpened = !isOpened)
    fun couldBeChecked(item: FilterItem) =
        filter.filter { it.checked }.size == 1 && item.checked
}