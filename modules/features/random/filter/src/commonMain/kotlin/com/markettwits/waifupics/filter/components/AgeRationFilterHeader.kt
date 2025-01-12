package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.theme.components.FontRubik
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.filter.generated.resources.Res
import waifupics.modules.features.random.filter.generated.resources.filter

@Composable
internal fun AgeRationFilterHeader(
    modifier: Modifier = Modifier.Companion,
    onClick: () -> Unit,
    isOpened: Boolean,
) =
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Res.string.filter),
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontRubik.medium(),
            fontSize = 16.sp
        )
        Icon(
            imageVector = if (!isOpened) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }