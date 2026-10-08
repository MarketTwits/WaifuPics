package com.markettwits.waifupics.navigation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import com.markettwits.waifupics.navigation.model.NavigationItem

@Composable
fun NavigationBody(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(0.dp),
    isDarkTheme: Boolean, selectedTitle: String = "Home", onClick: (NavigationItem) -> Unit,
    onClickChangeTheme: () -> Unit) {
    ModalDrawerSheet(modifier = modifier.width(264.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        windowInsets = WindowInsets(0, 0, 0, 0)) {
        Column(Modifier.padding(contentPadding).verticalScroll(rememberScrollState())
            .padding(horizontal = WaifuSpacing.item, vertical = WaifuSpacing.small),
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
            Text("Menu", Modifier.padding(horizontal = WaifuSpacing.small),
                style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
            IconButton(onClick = onClickChangeTheme) {
                Icon(if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = if (isDarkTheme) "Switch to light theme" else "Switch to dark theme",
                    modifier = Modifier.size(WaifuSize.overlayIcon), tint = MaterialTheme.colorScheme.primary)
            }
            items.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(item.title, style = MaterialTheme.typography.titleMedium) },
                    selected = selectedTitle == item.title,
                    onClick = { onClick(item) },
                    shape = MaterialTheme.shapes.medium,
                    icon = { Icon(item.icon(), null, Modifier.size(WaifuSize.overlayIcon)) }
                )
            }
        }
    }
}

private val items = listOf(NavigationItem.Home(), NavigationItem.Favorite(), NavigationItem.About())
