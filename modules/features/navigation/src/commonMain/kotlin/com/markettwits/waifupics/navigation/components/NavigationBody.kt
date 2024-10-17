package com.markettwits.waifupics.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.extensions.noRippleClickable
import com.markettwits.theme.components.FontRubik
import com.markettwits.theme.components.LightPink
import com.markettwits.waifupics.navigation.model.NavigationItem

@Composable
fun NavigationBody(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onClick: (NavigationItem) -> Unit,
    onClickChangeTheme : () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Menu",
                color = LightPink,
                fontSize = 14.sp,
                fontFamily = FontRubik.medium()
            )
            MenuItem(
                modifier = Modifier.noRippleClickable { onClickChangeTheme() },
                imageVector = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                title = "",
            )
            list.forEach {
                MenuItem(
                    modifier = Modifier
                        .noRippleClickable { onClick(it) },
                    icon = it.icon(),
                    title = it.title,
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    imageVector: ImageVector? = null,
    title: String,
) {
    Row(
        modifier
            .padding(10.dp)
            .padding(end = 80.dp)
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = modifier.width(10.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontFamily = FontRubik.medium()
        )
    }
}

private val list = listOf(
    NavigationItem.Home(),
    NavigationItem.Favorite(),
    NavigationItem.About()
)

