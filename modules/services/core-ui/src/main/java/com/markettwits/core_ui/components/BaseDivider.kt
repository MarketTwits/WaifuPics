package com.markettwits.core_ui.components

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.theme.DirtyWhite

@Composable
fun BaseDivider(modifier: Modifier = Modifier) {
    Divider(thickness = 0.2.dp, color = DirtyWhite.copy(alpha = 0.2f))
}