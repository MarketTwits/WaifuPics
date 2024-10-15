package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoImageBottomSheet(modifier : Modifier = Modifier, onDismiss: () -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
    ) {
        InfoImageContent(onDismiss = {
            scope.launch {
                bottomSheetState.hide()
                onDismiss()
            }
        })
    }
}