package com.markettwits.core_ui.base
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.R
import com.markettwits.core_ui.base_extensions.extractSiteName
import com.markettwits.waifupics.base.HyperlinkText
import com.markettwits.waifupics.base.openWebPage
import com.markettwits.waifupics.theame.theme.LightPink
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import java.util.Locale

@Composable
fun ImageParametersText(
    title: String,
    content: String,
    link: String,
) {
    if (content.isEmpty()) {
        Row {
            ImageParametersText(title = title, content = "No source available")
        }
    } else {
        HyperlinkText(
            fullText = "$title: \uD83D\uDD17 $content",
            hyperLinks = mutableMapOf(
                content to link,
            ),
            textStyle = TextStyle(
                color = LightPink,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            ),
            linkTextFontWeight = FontWeight.Medium,
            linkTextColor = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun ImageParametersText(
    title: String,
    content: String
) {
    Row {
        Text(
            text = "$title : ",
            color = LightPink,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp,
        )
        Text(
            text = content,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp,
        )
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
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnlinkMultiText(
    title: String,
    text : List<String>
){
    Row {
        Text(
            text = title,
            color = LightPink,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp
        )
        FlowRow {
            text.forEach {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
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
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp
        )
        LazyRow() {
            items(links) {
                val text = formatUrl(it)
                val context = LocalContext.current
                Text(
                    modifier = Modifier.clickable(onClick = {
                        openWebPage(it, context)
                    }),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

private fun formatUrl(url: String): String {
    val modified = url.extractSiteName()
    return "🔗 ${modified.substring(0, 1).uppercase(Locale.ROOT) + modified.substring(1)} "
}