package com.markettwits.random_image.presentation.components.bottom_pannel.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.R.font
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.components.progress.RotationRefreshIcon
import com.markettwits.random_image.R

@Composable
fun BasePanelItem(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    image: Int,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(Shapes.medium)
            .clickable(enabled = enabled) {
                onClick()
            }
            .background(if (!enabled) MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = image),
            tint = (if (!enabled) MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceTint),
            contentDescription = contentDescription
        )
    }
}
