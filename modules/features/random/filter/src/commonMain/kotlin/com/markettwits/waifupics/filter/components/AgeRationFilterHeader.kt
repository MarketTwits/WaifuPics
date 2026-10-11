package com.markettwits.waifupics.filter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.design.WaifuActionButton
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.filter.generated.resources.Res
import waifupics.modules.features.random.filter.generated.resources.done
import waifupics.modules.features.random.filter.generated.resources.filter

@Composable
internal fun AgeRationFilterHeader(modifier: Modifier = Modifier, onClick: () -> Unit, isOpened: Boolean) {
    WaifuActionButton(stringResource(if (isOpened) Res.string.done else Res.string.filter), onClick,
        modifier.fillMaxWidth())
}
