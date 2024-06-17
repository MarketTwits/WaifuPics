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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import com.markettwits.presentation.NavigationItem

@Composable
fun NavigationBody(modifier: Modifier = Modifier, onClick: (NavigationItem) -> Unit) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column{
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Menu",
                color = LightPink,
                fontSize = 14.sp,
                fontFamily = FontRubik.medium()
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
            contentDescription = item.title,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = modifier.width(10.dp))
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontFamily = FontRubik.medium()
        )
    }
}

private val list = listOf(
    NavigationItem.Home(),
    NavigationItem.Favorite(),
    NavigationItem.About()
)