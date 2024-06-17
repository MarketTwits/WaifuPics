package com.markettwits.core_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.base_extensions.extractSiteName
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import java.util.Locale


@Composable
fun ImageParametersText(
    title: String,
    content: String
) {
    if (content.isEmpty()) {
        Row {
            ImageParametersText(title = title, content = "No source available")
        }
    } else {
        Row {
            Text(
                text = "$title : ",
                color = LightPink,
                fontFamily = FontRubik.medium(),
                fontSize = 14.sp,
            )
            Text(
                text = content,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = FontRubik.medium(),
                fontSize = 14.sp,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnlinkMultiText(
    title: String,
    text: List<String>
) {
    Row {
        Text(
            text = title,
            color = LightPink,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp
        )
        FlowRow {
            text.forEach {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontRubik.medium(),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun MultyLinksText(
    title: String,
    links: List<String>
) {
    val uriHandler = LocalUriHandler.current
    Row {
        Text(
            text = title,
            color = LightPink,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp
        )
        LazyRow() {
            items(links) {
                val text = if (it.isEmpty()) " No data source" else formatUrl(it)
                Text(
                    modifier = Modifier.clickable(onClick = {
                        uriHandler.openUri(it)
                    }),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontRubik.medium(),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun SingleLinkText(
    title: String,
    link: String
) {
    Row {
        Text(
            text = title,
            color = LightPink,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp
        )
        val text = if (link.isEmpty()) "No data source" else formatUrl(link)
        val uriHandler = LocalUriHandler.current
        Text(
            modifier = Modifier.clickable(onClick = {
                uriHandler.openUri(link)
            }),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp,
        )
    }
}

private fun formatUrl(url: String): String {
    val modified = url.extractSiteName()
    return "🔗 ${modified.substring(0, 1).uppercase(Locale.ROOT) + modified.substring(1)} "
}
