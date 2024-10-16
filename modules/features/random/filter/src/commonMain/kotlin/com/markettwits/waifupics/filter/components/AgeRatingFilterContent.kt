package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.BaseDivider
import com.markettwits.waifupics.filter.model.FilterItem
import com.markettwits.waifupics.filter.model.FilterState

@Composable
internal fun AgeRatingFilterContent(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    toggleScreen: () -> Unit,
    onItemClick: (FilterItem) -> Unit
) = Column(modifier = modifier) {
    BaseDivider()
    AgeRatingFilterBody(filterState = filterState) {
        onItemClick(it)
    }
    AgeRationFilterHeader(
        onClick = { toggleScreen() },
        isOpened = filterState.isOpened
    )
}