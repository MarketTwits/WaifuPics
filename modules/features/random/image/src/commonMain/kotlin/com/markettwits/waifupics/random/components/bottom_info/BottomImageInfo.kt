package com.markettwits.waifupics.random.components.bottom_info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.model.ImageSourceUi
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomImageInfo(
    modifier: Modifier = Modifier,
    imageData: ImageSourceUi,
    colorPalette: List<List<Int>>,
    loadedImage: ImageState.Success?,
    compact: Boolean,
    infoEnabled: Boolean,
    onInfo: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val source = imageData.sourceUrl
    val hasSource = source.startsWith("https://") || source.startsWith("http://")
    val title = if (hasSource) source.substringAfter("://").substringBefore('/').removePrefix("www.")
        else "WaifuPics"
    val infoDescription = stringResource(Res.string.image_information)
    Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            title,
            modifier = Modifier.clickable(enabled = hasSource) { uriHandler.openUri(source) },
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = com.markettwits.theme.components.FontRubik.medium(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (!compact) imageData.artistName?.let { artist ->
            Text(artist, color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f),
                fontSize = 13.sp, lineHeight = 18.sp,
                fontFamily = com.markettwits.theme.components.FontRubik.regular(),
                maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(imageRatingLabel(imageData.ageRating),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                fontSize = 13.sp, lineHeight = 18.sp,
                fontFamily = com.markettwits.theme.components.FontRubik.regular())
            loadedImage?.let { image ->
                Text("${image.width} × ${image.height}",
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                    fontSize = 12.sp, lineHeight = 18.sp,
                    fontFamily = com.markettwits.theme.components.FontRubik.regular())
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            // The existing palette stays on the feed; the sheet contains its exact values.
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                colorPalette.filter { it.size >= 3 }.take(6).forEach { rgb ->
                    Box(Modifier.size(12.dp).background(
                        Color(rgb[0].coerceIn(0, 255), rgb[1].coerceIn(0, 255), rgb[2].coerceIn(0, 255)), CircleShape
                    ))
                }
            }
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = onInfo,
                enabled = infoEnabled,
                modifier = Modifier.heightIn(min = 48.dp).semantics { contentDescription = infoDescription },
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ) {
                Icon(Icons.Outlined.Info, contentDescription = null, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(6.dp))
                Text(stringResource(Res.string.image_info), fontSize = 12.sp)
            }
        }
    }
}

@Composable
internal fun imageRatingLabel(rating: String): String = when (rating) {
    "safe" -> stringResource(Res.string.safe_for_work)
    "suggestive" -> stringResource(Res.string.suggestive)
    "borderline" -> stringResource(Res.string.borderline)
    "explicit" -> stringResource(Res.string.explicit_18)
    else -> rating.ifBlank { stringResource(Res.string.image_not_provided) }
}
