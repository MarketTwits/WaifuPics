package com.markettwits.waifupics.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.R

@Composable
fun BasePanelItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    image: Int,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = false) {
                onClick()
            }
            .background(if (isLoading) MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = image),
            tint = (if (isLoading) MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceTint),
            contentDescription = contentDescription
        )
    }
}

@Composable
fun RefreshPanelItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
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
                text = "Refresh !",
                color = MaterialTheme.colorScheme.surfaceTint,
                fontFamily = FontFamily(Font(R.font.rubik_medium)),
                fontSize = 16.sp
            )
        }

    }
}
