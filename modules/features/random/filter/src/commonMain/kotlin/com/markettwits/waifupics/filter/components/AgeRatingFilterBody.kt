package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.theme.components.FontRubik
import com.markettwits.theme.components.LightPink
import com.markettwits.waifupics.filter.model.FilterItem
import com.markettwits.waifupics.filter.model.FilterState
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.filter.generated.resources.Res
import waifupics.modules.features.random.filter.generated.resources.age_rating

@Composable
internal fun AgeRatingFilterBody(
    modifier: Modifier = Modifier.Companion,
    filterState: FilterState,
    selectedItem: (FilterItem) -> Unit,
) = Column(
    modifier = modifier
        .fillMaxWidth()
) {
    Text(
        modifier = Modifier.Companion.padding(10.dp),
        text = stringResource(Res.string.age_rating),
        color = LightPink,
        fontFamily = FontRubik.medium(),
        fontSize = 12.sp
    )
    Column(
        modifier = Modifier.Companion.height(250.dp),
    ) {
        filterState.filter.forEach {
            AgeRatingFilterPosition(
                canBeChecked = filterState.couldBeChecked(it),
                item = it
            ) {
                selectedItem.invoke(it)
            }
        }
    }
}