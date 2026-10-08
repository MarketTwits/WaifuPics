package com.markettwits.waifupics.random.components.image_state.fuckup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.image_load_failed
import waifupics.modules.features.random.image.generated.resources.image_load_hint
import waifupics.modules.features.random.image.generated.resources.retry_loading

@Composable
internal fun ImageFuckup(onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 80.dp), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier.widthIn(max = 320.dp).fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.inversePrimary
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(stringResource(Res.string.image_load_failed), fontSize = 18.sp, lineHeight = 24.sp,
                    textAlign = TextAlign.Center, fontFamily = com.markettwits.theme.components.FontRubik.medium())
                Text(stringResource(Res.string.image_load_hint), fontSize = 13.sp, lineHeight = 18.sp, textAlign = TextAlign.Center,
                    maxLines = 3, overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.75f))
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )) { Text(stringResource(Res.string.retry_loading)) }
            }
        }
    }
}
