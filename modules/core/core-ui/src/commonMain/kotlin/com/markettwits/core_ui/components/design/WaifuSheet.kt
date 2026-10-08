package com.markettwits.core_ui.components.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.core.core_ui.generated.resources.Res
import waifupics.modules.core.core_ui.generated.resources.close

/** Shared sheet surface. Content controls scrolling, so horizontal lists can reach both edges. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaifuSheet(
    title: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
    var closing by remember(sheetState) { mutableStateOf(false) }
    var dismissed by remember(sheetState) { mutableStateOf(false) }
    val dismissOnce: () -> Unit = {
        if (!dismissed) {
            dismissed = true
            onDismissRequest()
        }
    }
    val closeSheet: () -> Unit = {
        if (!closing) {
            closing = true
            scope.launch {
                try {
                    sheetState.hide()
                    if (!sheetState.isVisible) dismissOnce()
                } finally {
                    closing = false
                }
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = dismissOnce,
        modifier = modifier.semantics { paneTitle = title },
        sheetState = sheetState,
        sheetMaxWidth = WaifuSize.sheetWidth,
        shape = MaterialTheme.shapes.extraLarge.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant) }
    ) {
        WaifuSheetHeader(title, closeSheet)
        content(closeSheet)
    }
}

@Composable
private fun WaifuSheetHeader(title: String, onClose: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(start = WaifuSpacing.screen, end = WaifuSpacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f).semantics { heading() },
            style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = onClose, modifier = Modifier.size(WaifuSize.touchTarget)) {
            Icon(Icons.Default.Close, stringResource(Res.string.close), Modifier.size(WaifuSize.icon))
        }
    }
}
