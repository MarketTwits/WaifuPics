package com.markettwits.waifupics.random.components.bottom_pannel.buttons

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.layout.size
import com.markettwits.theme.components.WaifuSize

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun BasePanelItem(modifier: Modifier = Modifier, enabled: Boolean, image: Painter,
    contentDescription: String = "", onClick: () -> Unit) {
    FilledTonalIconButton(onClick = onClick, modifier = modifier, enabled = enabled,
        shapes = IconButtonShapes(MaterialTheme.shapes.medium, MaterialTheme.shapes.small)) {
        Icon(image, contentDescription, Modifier.size(WaifuSize.icon))
    }
}
