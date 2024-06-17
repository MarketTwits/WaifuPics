package com.markettwits.random_image.presentation.components.filter.data

import com.markettwits.random_image.presentation.components.filter.presentation.FilterItem

interface FilterDataSource {
    fun filter(): List<FilterItem>
    class Base() : FilterDataSource {
        override fun filter() = listOf(
            FilterItem(1, "safe_for_work", "safe", true),
            FilterItem(2, "suggestive", "suggestive"),
            FilterItem(3, "Borderline", "borderline"),
            FilterItem(4, "explicit_18", "explicit")
        )
    }
}