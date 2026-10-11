package com.markettwits.waifupics.random.components.image_state.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import com.markettwits.core_ui.components.design.WaifuLoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.loading_image

@Composable
internal fun ImageLoading(modifier: Modifier = Modifier) {
    val label = stringResource(Res.string.loading_image)
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        WaifuLoadingIndicator(Modifier.semantics { contentDescription = label })
    }
}
