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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.base_extensions.extractSiteName
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import com.markettwits.core_ui.theme.WaifuPicsTheme
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
                fontFamily = FontRubik.medium,
                fontSize = 14.sp,
            )
            Text(
                text = content,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = FontRubik.medium,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WaifuPicsTheme {
        val inputUrls = listOf(
            "http://www.pixiv.net/en/users/30837811",
            "https://www.example.com",
            "http://www.openai.com",
            "www.wikipedia.org"
        )
        MultyLinksText(
            title = "Links: ",
            links = inputUrls
        )
    }
}

@Preview
@Composable
private fun PreviewImageParameters() {
    WaifuPicsTheme {

        MultyLinksText(
            title = "Image source",
            links = listOf("https://danbooru.donmai.us/post/show/4906121")
        )
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
            fontFamily = FontRubik.medium,
            fontSize = 14.sp
        )
        FlowRow {
            text.forEach {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontRubik.medium,
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
    Row {
        Text(
            text = title,
            color = LightPink,
            fontFamily = FontRubik.medium,
            fontSize = 14.sp
        )
        LazyRow() {
            items(links) {
                val text = if (it.isNullOrEmpty()) " No data source" else formatUrl(it)
                val context = LocalContext.current
                Text(
                    modifier = Modifier.clickable(onClick = {
                        openWebPage(it, context)
                    }),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontRubik.medium,
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
            fontFamily = FontRubik.medium,
            fontSize = 14.sp
        )
        val text = if (link.isNullOrEmpty()) "No data source" else formatUrl(link)
        val context = LocalContext.current
        Text(
            modifier = Modifier.clickable(onClick = {
                openWebPage(link, context)
            }),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontRubik.medium,
            fontSize = 14.sp,
        )
    }
}

private fun formatUrl(url: String): String {
    val modified = url.extractSiteName()
    return "🔗 ${modified.substring(0, 1).uppercase(Locale.ROOT) + modified.substring(1)} "
}
