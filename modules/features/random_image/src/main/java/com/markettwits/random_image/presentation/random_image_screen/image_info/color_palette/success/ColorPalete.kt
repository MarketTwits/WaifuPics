package com.markettwits.waifupics.view.main.ui.image_info.color_palette.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.R
import com.markettwits.core_ui.base_extensions.rgbToComposeColor
import com.markettwits.waifupics.theame.theme.LightPink

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
                text = "Color palette:",
                fontFamily = FontFamily(Font(R.font.rubik_medium)),
                color = LightPink,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
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
