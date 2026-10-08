package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import com.markettwits.waifupics.filter.model.FilterItem
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AgeRatingFilterPosition(canBeChecked: Boolean, item: FilterItem, onClick: (FilterItem) -> Unit) {
    Surface(shape = MaterialTheme.shapes.medium,
        color = if (item.checked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer) {
        Row(Modifier.fillMaxWidth().heightIn(min = WaifuSize.touchTarget)
            .toggleable(value = item.checked, enabled = !canBeChecked, role = Role.Checkbox,
                onValueChange = { onClick(item) })
            .padding(horizontal = WaifuSpacing.content, vertical = WaifuSpacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            Checkbox(checked = item.checked, onCheckedChange = null, enabled = !canBeChecked)
            Text(stringResource(item.title), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
