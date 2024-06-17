package com.markettwits.random_image.presentation.components.bottom_pannel.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.components.progress.RotationRefreshIcon
import com.markettwits.core_ui.theme.FontRubik
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.refresh

@Composable
fun RefreshPanelItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(Shapes.medium)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RotationRefreshIcon(isLoading)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(Res.string.refresh),
                color = MaterialTheme.colorScheme.surfaceTint,
                overflow = TextOverflow.Clip,
                fontFamily = FontRubik.medium(),
                fontSize = 16.sp
            )
        }

    }
}