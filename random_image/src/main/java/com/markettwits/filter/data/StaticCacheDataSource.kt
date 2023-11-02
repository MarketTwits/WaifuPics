package com.markettwits.filter.data

import com.markettwits.filter.presentation.FilterItem

interface StaticCacheDataSource {
    fun filter(): List<FilterItem>
    class Base() : StaticCacheDataSource {
        override fun filter() = listOf(
            FilterItem(1, "Safe for work", "safe", true),
            FilterItem(2, "Suggestive", "suggestive"),
            FilterItem(3, "Borderline", "borderline"),
            FilterItem(4, "Explicit (18+)", "explicit")
        )
    }
}