package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.design.WaifuSheet
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.filter.generated.resources.Res
import waifupics.modules.features.random.filter.generated.resources.age_rating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeRatingFilter(modifier: Modifier = Modifier, viewModel: AgeRatingFilterViewModel) {
    val filterState by viewModel.state().collectAsState()
    if (filterState.isOpened) {
        WaifuSheet(title = stringResource(Res.string.age_rating), modifier = modifier,
            onDismissRequest = viewModel::toggle) { dismissSheet ->
            AgeRatingFilterContent(
                modifier = Modifier.padding(horizontal = WaifuSpacing.screen, vertical = WaifuSpacing.content),
                filterState = filterState, toggleScreen = dismissSheet, onItemClick = viewModel::checked
            )
        }
    }
}
