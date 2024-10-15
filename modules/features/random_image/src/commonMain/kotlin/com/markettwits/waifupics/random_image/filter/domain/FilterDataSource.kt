package com.markettwits.waifupics.random_image.filter.domain

import com.markettwits.waifupics.random_image.filter.model.FilterItem
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.borderline
import waifupics.modules.features.random_image.generated.resources.explicit_18
import waifupics.modules.features.random_image.generated.resources.safe_for_work
import waifupics.modules.features.random_image.generated.resources.suggestive

interface FilterDataSource {

    fun filter(): List<FilterItem>

    class Base() : FilterDataSource {
        override fun filter() = listOf(
            FilterItem(1, Res.string.safe_for_work, "safe", true),
            FilterItem(2, Res.string.suggestive, "suggestive"),
            FilterItem(3, Res.string.borderline, "borderline"),
            FilterItem(4, Res.string.explicit_18, "explicit")
        )
    }
}