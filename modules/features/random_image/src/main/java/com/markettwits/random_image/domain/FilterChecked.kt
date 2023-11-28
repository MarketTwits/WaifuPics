package com.markettwits.random_image.domain

import com.markettwits.random_image.presentation.components.filter.presentation.FilterItem

interface FilterChecked {
    fun checked(selectedItem : FilterItem, currentList : List<FilterItem>) : List<FilterItem>
    fun mapFilter(value : List<FilterItem>) :  List<String>

    class Base : FilterChecked {
        override fun checked(selectedItem : FilterItem, currentList : List<FilterItem>) : List<FilterItem> {
            return currentList.map { if (it.id == selectedItem.id) selectedItem else it }
        }
        override fun mapFilter(value: List<FilterItem>): List<String> {
            val checked = value.filter { it.checked }
            return checked.map { it.value }
        }
    }
}