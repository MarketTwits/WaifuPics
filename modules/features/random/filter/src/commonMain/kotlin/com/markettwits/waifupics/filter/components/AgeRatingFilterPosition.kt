package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.markettwits.theme.components.FontRubik
import com.markettwits.waifupics.filter.model.FilterItem
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AgeRatingFilterPosition(
    canBeChecked: Boolean,
    item: FilterItem,
    onClick: (FilterItem) -> Unit,
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable {
                if (!canBeChecked) onClick(item)
            },
        verticalAlignment = Alignment.Companion.CenterVertically
    )
    {
        Checkbox(
            checked = item.checked,
            onCheckedChange = { onClick(item) })
        Text(
            text = stringResource(item.title),
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp
        )
    }
}