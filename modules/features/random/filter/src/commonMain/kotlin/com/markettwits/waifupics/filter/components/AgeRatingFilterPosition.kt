package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
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
            .heightIn(min = 48.dp)
            .clickable {
                if (!canBeChecked) onClick(item)
            },
        verticalAlignment = Alignment.Companion.CenterVertically
    )
    {
        Checkbox(
            checked = item.checked,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onBackground,
                uncheckedColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.65f),
                checkmarkColor = MaterialTheme.colorScheme.background
            ),
            onCheckedChange = { onClick(item) })
        Text(
            text = stringResource(item.title),
            color = MaterialTheme.colorScheme.inversePrimary,
            fontFamily = FontRubik.medium(),
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
    }
}
