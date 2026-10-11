package com.markettwits.core_ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Composable
fun HyperlinkText(modifier: Modifier = Modifier, fullText: String,
    fullTextColor: Color = MaterialTheme.colorScheme.onSurface,
    linkText: List<String>, linkTextColor: Color = MaterialTheme.colorScheme.secondary,
    linkTextFontFamily: FontFamily = MaterialTheme.typography.bodyLarge.fontFamily ?: FontFamily.Default,
    linkTextDecoration: TextDecoration = TextDecoration.Underline, hyperlinks: List<String>,
    fontSize: TextUnit = TextUnit.Unspecified) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val start = fullText.indexOf(link)
            val url = hyperlinks.getOrNull(index)
            if (start >= 0 && link.isNotEmpty() && !url.isNullOrBlank()) {
                addLink(LinkAnnotation.Url(url, TextLinkStyles(SpanStyle(
                    color = linkTextColor, fontFamily = linkTextFontFamily, textDecoration = linkTextDecoration))),
                    start, start + link.length)
            }
        }
    }
    Text(annotatedString, modifier, color = fullTextColor,
        style = if (fontSize == TextUnit.Unspecified) MaterialTheme.typography.bodyLarge
            else MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize))
}
