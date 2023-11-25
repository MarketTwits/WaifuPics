package com.markettwits.presentation.screens.detail.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.markettwits.presentation.animations.Animation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoImageBottomSheet(onDismiss: () -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
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