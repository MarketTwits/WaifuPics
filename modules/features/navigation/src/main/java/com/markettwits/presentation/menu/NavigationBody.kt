package com.markettwits.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.R
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.presentation.NavigationItem
import com.markettwits.core_ui.theme.LightPink

@Composable
@Preview
private fun NavigationBodyPrview() {
    WaifuPicsTheme(darkTheme = true) {
        NavigationBody(){}
    }
}

@Composable
fun NavigationBody(modifier: Modifier = Modifier, onClick: (NavigationItem) -> Unit) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                ,
                text = "Menu",
                color = LightPink,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            )
            list.forEach {
                MenuItem(
                    modifier = Modifier.clickable { onClick(it) },
                    item = it
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    item: NavigationItem,
) {
    Row(
        modifier
            .padding(10.dp)
            .padding(end = 80.dp)

    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = stringResource(id = item.title),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = modifier.width(10.dp))
        Text(
            text = stringResource(id = item.title),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontFamily = FontFamily(
                Font(R.font.rubik_medium)
            )
        )
    }
}

private val list = listOf(
    NavigationItem.Home(),
    NavigationItem.Favorite(),
    NavigationItem.About()
)