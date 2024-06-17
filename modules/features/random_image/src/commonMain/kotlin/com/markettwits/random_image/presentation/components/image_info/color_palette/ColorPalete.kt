package com.markettwits.waifupics.view.main.ui.image_info.color_palette.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.base_extensions.rgbToComposeColor
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.color_palette

@Composable
fun ColorPalette(hex : List<List<Int>>) {
    val pallete = remember {
        mutableStateOf(hex)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = stringResource(Res.string.color_palette),
                fontFamily = FontRubik.medium(),
                color = LightPink,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Shapes.medium),
                horizontalArrangement = Arrangement.End
            ) {
                pallete.value.forEach {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(rgbToComposeColor(it))
                            .weight(1f)
                    ) {}
                }
            }
        }
    }
}
