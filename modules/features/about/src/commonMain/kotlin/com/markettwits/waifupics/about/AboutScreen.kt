package com.markettwits.waifupics.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.components.text.HyperlinkText
import com.markettwits.core_ui.components.image.DefaultImages
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.Pink
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.about.generated.resources.Res
import waifupics.modules.features.about.generated.resources.a_simple_api_for_nekos
import waifupics.modules.features.about.generated.resources.description_text
import waifupics.modules.features.about.generated.resources.documentation_text
import waifupics.modules.features.about.generated.resources.freecodecamp_url
import waifupics.modules.features.about.generated.resources.github
import waifupics.modules.features.about.generated.resources.https_github_com_nekos_api_nekos_api
import waifupics.modules.features.about.generated.resources.nekosapi_logo
import waifupics.modules.features.about.generated.resources.source_code
import waifupics.modules.features.about.generated.resources.source_code_link_text
import waifupics.modules.features.about.generated.resources.this_page
import waifupics.modules.features.about.generated.resources.usage_text


@Composable
fun AboutScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = DefaultImages.favicon(),
                alignment = Alignment.TopStart,
                contentDescription = stringResource(Res.string.nekosapi_logo),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            // display the title text
            Text(
                text = "NekosApi",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(Res.string.a_simple_api_for_nekos),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = stringResource(Res.string.description_text),
            fontSize = 16.sp
        )
        Text(
            text = "Features",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        BulletList(
            items = listOf(
                "Random Neko images",
                "Random Neko facts",
                "Random Neko quotes",
                "Random Neko wallpapers",
                "Random Neko emojis",
                "And more..."
            )
        )
        Text(
            text = "Usage",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(Res.string.usage_text),
            fontSize = 16.sp
        )
        HyperlinkText(
            fontSize = 16.sp,
            linkTextFontFamily = FontRubik.medium(),
            fullTextColor = MaterialTheme.colorScheme.onPrimary,
            linkTextColor = Pink,
            fullText = stringResource(Res.string.documentation_text),
            linkText = listOf(stringResource(Res.string.this_page)),
            hyperlinks = listOf(stringResource(Res.string.freecodecamp_url))
        )
        Text(
            text = stringResource(Res.string.source_code),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        HyperlinkText(
            fontSize = 16.sp,
            linkTextFontFamily = FontRubik.medium(),
            fullTextColor = MaterialTheme.colorScheme.onPrimary,
            linkTextColor = Pink,
            fullText = stringResource(Res.string.source_code_link_text),
            linkText = listOf(stringResource(Res.string.github)),
            hyperlinks = listOf(stringResource(Res.string.https_github_com_nekos_api_nekos_api))
        )
    }
}

@Composable
private fun BulletList(items: List<String>) {
    for (item in items) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Brightness1,
                contentDescription = null,
                modifier = Modifier.size(8.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item,
                fontSize = 16.sp
            )
        }
    }
}