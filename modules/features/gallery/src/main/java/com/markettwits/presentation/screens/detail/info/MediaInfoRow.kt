package com.markettwits.presentation.screens.detail.info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    content: String,
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape.copy(CornerSize(10.dp)))
            .combinedClickable(
                onClick = { onClick?.let { it() } },
                onLongClick = {
                    if (onLongClick != null) onLongClick()
                    else {
                        clipboardManager.setText(AnnotatedString(content))
                    }
                }
            ),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        headlineContent = {
            Text(
                text = label,
                fontWeight = FontWeight.Medium
            )
        },
        supportingContent = {
            Text(text = content)
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    )
}