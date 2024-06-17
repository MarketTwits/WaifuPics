package com.markettwits.random_image.presentation.components.image.fuckup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.theme.FontRubik
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.senpai
import waifupics.modules.features.random_image.generated.resources.something_wrong

@Composable
fun ImageFuckup(message : String) {
    val scaleFactor = 0.6f
    Box(
        modifier = Modifier
            .padding(30.dp)
            .height(350.dp)
            .fillMaxSize()
            .clip(Shapes.medium)
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .scale(scaleFactor)
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .aspectRatio(1f),
                painter = org.jetbrains.compose.resources.painterResource(Res.drawable.senpai),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(Res.string.something_wrong),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontRubik.medium()
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontFamily = FontRubik.regular()
            )
        }

    }
}