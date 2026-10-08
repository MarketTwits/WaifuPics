package com.markettwits.waifupics.random.components.image_state.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.loading_image

@Composable
internal fun ImageLoading(modifier: Modifier = Modifier) {
    val label = stringResource(Res.string.loading_image)
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(28.dp).semantics { contentDescription = label },
            color = MaterialTheme.colorScheme.onBackground,
            strokeWidth = 2.dp
        )
    }
}
