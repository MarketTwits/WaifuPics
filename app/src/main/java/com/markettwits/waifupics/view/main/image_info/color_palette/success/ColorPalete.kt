package com.markettwits.waifupics.view.main.image_info.color_palette.success

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.R
import com.markettwits.waifupics.ui.theme.LightPink
import com.markettwits.waifupics.view.main.fake.fakeHex
import com.markettwits.waifupics.view.main.fake.hexToComposeColor

@Preview
@Composable
fun ColorPalette() {
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
            val colors = fakeHex()
            Row(
                modifier = Modifier.fillMaxWidth() .clip(RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.End
            ) {
                colors.forEach {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(hexToComposeColor(it))
                            .weight(1f)
                    ) {}
                }
            }
        }
    }
}