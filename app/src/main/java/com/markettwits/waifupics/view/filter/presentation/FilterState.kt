package com.markettwits.waifupics.view.filter.presentation

data class FilterState(
    val isOpened : Boolean = false,
    val filter : List<FilterItem> = emptyList()
){
    fun toggle() = this.copy(isOpened = !isOpened)
}