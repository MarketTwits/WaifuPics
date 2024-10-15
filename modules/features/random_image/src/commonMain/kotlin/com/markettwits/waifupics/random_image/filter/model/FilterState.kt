package com.markettwits.waifupics.random_image.filter.model


data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
) {
    fun toggle() = this.copy(isOpened = !isOpened)
    fun couldBeChecked(item: FilterItem) =
        filter.filter { it.checked }.size == 1 && item.checked
}