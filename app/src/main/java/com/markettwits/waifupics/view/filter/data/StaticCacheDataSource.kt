package com.markettwits.waifupics.view.filter.data

import com.markettwits.waifupics.view.filter.presentation.FilterItem

interface StaticCacheDataSource {
    fun filter(): List<FilterItem>
    class Base() : StaticCacheDataSource {
        override fun filter() = listOf(
            FilterItem(1, "Safe for work", "sfw", true),
            FilterItem(2, "Questionable", "questionable"),
            FilterItem(3, "Suggestive", "suggestive"),
            FilterItem(4, "Borderline", "borderline"),
            FilterItem(5, "Explicit (18+)", "explicit")
        )
    }
}