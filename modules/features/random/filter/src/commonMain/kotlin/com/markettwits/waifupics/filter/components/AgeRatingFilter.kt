package com.markettwits.waifupics.filter.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeRatingFilter(
    modifier: Modifier = Modifier
) {
    val viewModel: AgeRatingFilterViewModel = ApplicationViewModel()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val filterState by viewModel.state().collectAsState()
    Box(
        modifier = modifier
            .padding(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
            .padding(5.dp)
            .clip(com.markettwits.theme.components.Shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .animateContentSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        AgeRationFilterHeader(
            onClick = { viewModel.toggle() },
            isOpened = filterState.isOpened
        )
        if (filterState.isOpened) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.toggle() },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                AgeRatingFilterContent(
                    filterState = filterState,
                    toggleScreen = { viewModel.toggle() },
                ) {
                    viewModel.checked(it)
                }
            }
        }
    }
}
