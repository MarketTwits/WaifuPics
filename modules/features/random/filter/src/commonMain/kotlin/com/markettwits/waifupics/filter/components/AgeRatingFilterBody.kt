package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.filter.model.FilterItem
import com.markettwits.waifupics.filter.model.FilterState

@Composable
internal fun AgeRatingFilterBody(modifier: Modifier = Modifier, filterState: FilterState,
    selectedItem: (FilterItem) -> Unit) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
        filterState.filter.forEach { item ->
            AgeRatingFilterPosition(canBeChecked = filterState.couldBeChecked(item), item = item,
                onClick = selectedItem)
        }
    }
}
