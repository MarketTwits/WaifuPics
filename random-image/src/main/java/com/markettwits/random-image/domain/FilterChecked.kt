package com.markettwits.waifupics.view.main.domain

import com.markettwits.filter.presentation.FilterItem

interface FilterChecked {
    fun checked(selectedItem : FilterItem, currentList : List<FilterItem>) : List<FilterItem>
    fun mapFilter(value : List<FilterItem>) : String

    class Base : FilterChecked{
        override fun checked(selectedItem : FilterItem, currentList : List<FilterItem>) : List<FilterItem> {
            return currentList.map { if (it.id == selectedItem.id) selectedItem else it }
        }
        override fun mapFilter(value : List<FilterItem>): String {
            val checked = value.filter { it.checked }
            val formatString = checked.joinToString(",") { it.value }
            return formatString.ifEmpty { "sfw" }
        }
    }
}