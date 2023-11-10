package com.markettwits.presentation.detail.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.presentation.detail.GalleryScreenViewModel


//@Preview
//@Composable
//private fun TopBarPreview() {
//    WaifuPicsTheme {
//        TopBarScreenImage()
//    }
//}

@Composable
fun TopBarScreenImage(viewModel: GalleryScreenViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.clickable {
                    LocalNavigationState.rootNavigation.getNavController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "created",
                modifier = Modifier.padding(start = 30.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
            ItemDropDownMenu(setAs = {
                viewModel.setImageAs()
            }, info = {
                viewModel.infoAboutImage()
            }, saveToGallery = {
                viewModel.saveToGallery()
            })
        }
    }

}