package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeRatingFilter(
    modifier: Modifier = Modifier,
    viewModel: AgeRatingFilterViewModel,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val filterState by viewModel.state().collectAsState()

    if (filterState.isOpened) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = viewModel::toggle,
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.inversePrimary,
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.3f))
            }
        ) {
            AgeRatingFilterContent(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                filterState = filterState,
                toggleScreen = viewModel::toggle,
                onItemClick = viewModel::checked
            )
        }
    }
}
