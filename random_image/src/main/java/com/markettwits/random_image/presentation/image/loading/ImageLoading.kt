package com.markettwits.random_image.presentation.image.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.R
import com.markettwits.core_ui.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.base.RoundedLinearProgressIndicator
import com.markettwits.waifupics.theame.theme.Pink


@Composable
fun ImageLoading(modifier: Modifier = Modifier) {
    val scaleFactor = 0.35f // Adjust this value to control the size reduction
    Box(
        modifier = modifier
            .padding(20.dp)
            .height(350.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scaleFactor)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favicon),
                contentDescription = null,
                tint = Pink
            )
            Spacer(modifier = Modifier.height(10.dp))
            RoundedLinearProgressIndicator()
        }
    }
}
@Preview
@Composable
private fun ImageLoadingPreview() {
    WaifuPicsTheme(darkTheme = true) {
        ImageLoading(Modifier)
    }
}
