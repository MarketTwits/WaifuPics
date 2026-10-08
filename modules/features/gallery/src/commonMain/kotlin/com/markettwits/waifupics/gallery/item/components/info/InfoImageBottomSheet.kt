package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.liquid_glass.LiquidGlassContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoImageBottomSheet(modifier : Modifier = Modifier, onDismiss: () -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        modifier = modifier,
        containerColor = Color.Transparent,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
    ) {
        LiquidGlassContainer(
            blurRadius = 30.dp,
            backgroundAlpha = 0.55f,
            tintColor = Color.White,
            cornerRadius = 28.dp
        ) {
            InfoImageContent(
                modifier = Modifier.padding(16.dp),
                onDismiss = {
                    scope.launch {
                        bottomSheetState.hide()
                        onDismiss()
                    }
                }
            )
        }
    }
}