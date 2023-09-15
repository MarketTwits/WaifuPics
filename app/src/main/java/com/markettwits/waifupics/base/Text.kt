package com.markettwits.waifupics.base

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.R
import com.markettwits.waifupics.ui.theme.DirtyWhite
import com.markettwits.waifupics.ui.theme.LightPink

@Composable
fun ImageParametersText(
    title: String,
    content: String,
    link: String,
) {
    HyperlinkText(
        fullText = "$title: $content",
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

@Composable
fun MultiImageParametersText(
    title: String,
    content: List<String>,
    link: List<String>
) {
    val outputString = content.joinToString(" ")
    val mutableMap = mutableMapOf<String, String>()
    content.forEachIndexed { index, s ->
        mutableMap.put(s, link[index])
    }
    HyperlinkText(
        fullText = "$title: $outputString",
        hyperLinks = mutableMap,
        textStyle = TextStyle(
            color = LightPink,
            fontFamily = FontFamily(Font(R.font.rubik_medium))
        ),
        linkTextFontWeight = FontWeight.Medium,
        linkTextColor = MaterialTheme.colorScheme.onPrimary,
        fontSize = 14.sp,
    )
}
