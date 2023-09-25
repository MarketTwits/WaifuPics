package com.markettwits.waifupics.navigation.view.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.presentation.NavigationItem

@Composable
fun NavigationPanel(
    list: List<NavigationItem>,
    navItemClick: (NavigationItem) -> Unit
) {
    Column(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            list.forEach {
                Text(
                    modifier = Modifier
                        .clickable {
                            navItemClick(it)
                        }
                        .weight(1f),
                    text = stringResource(id = it.title),
                    color = if (!it.isSelected) Color.Gray else MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}