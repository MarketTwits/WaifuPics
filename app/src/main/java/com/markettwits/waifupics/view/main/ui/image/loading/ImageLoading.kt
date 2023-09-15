package com.markettwits.waifupics.view.main.ui.image.loading

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
import com.markettwits.waifupics.R
import com.markettwits.waifupics.ui.theme.Pink
import com.markettwits.waifupics.ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.base.RoundedLinearProgressIndicator


@Composable
fun ImageLoading() {
    val scaleFactor = 0.3f // Adjust this value to control the size reduction
    Box(
        modifier = Modifier
            .padding(30.dp)
            .height(350.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scaleFactor) // Apply scaling to the Column
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
        ImageLoading()
    }
}
