package com.markettwits.random_image.presentation.components.filter.data

import com.markettwits.core.wrappers.ManageResource
import com.markettwits.random_image.R
import com.markettwits.random_image.presentation.components.filter.presentation.FilterItem

interface FilterDataSource {
    fun filter(): List<FilterItem>
    class Base(private val manageResource: ManageResource) : FilterDataSource {
        override fun filter() = listOf(
            FilterItem(1, manageResource.string(R.string.safe_for_work), "safe", true),
            FilterItem(2, manageResource.string(R.string.suggestive), "suggestive"),
            FilterItem(3, manageResource.string(R.string.borderline), "borderline"),
            FilterItem(4, manageResource.string(R.string.explicit_18), "explicit")
        )
    }
}