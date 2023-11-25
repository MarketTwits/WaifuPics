/*
 * SPDX-FileCopyrightText: 2023 IacobIacob01
 * SPDX-License-Identifier: Apache-2.0
 */

package com.markettwits.presentation.screens.list.seledted

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.DriveFileMove
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.markettwits.presentation.screens.detail.ImageFavoriteUi
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SelectionSheet(
    modifier: Modifier = Modifier,
    selectedMedia: List<ImageFavoriteUi>,
    selectionState: MutableState<Boolean>,
) {
    fun clearSelection() {
       // selectedMedia.clear()
        selectionState.value = false
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val trashSheetState = remember{
        mutableStateOf(false)
    }
    val moveSheetState = remember{
        mutableStateOf(false)
    }
//    val result = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult(),
//        onResult = {
//            if (it.resultCode == Activity.RESULT_OK) {
//                clearSelection()
//                if (trashSheetState.isVisible) {
//                    scope.launch {
//                        trashSheetState.hide()
//                    }
//                }
//            }
//        }
//    )
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
    val tabletMode = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact
    val sizeModifier = Modifier.fillMaxWidth()
//        if (!tabletMode) Modifier.fillMaxWidth()
   // else Modifier.wrapContentWidth()
    AnimatedVisibility(
        modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars),
        visible = selectionState.value,
        enter = slideInVertically { it * 2 },
        exit = slideOutVertically { it * 2 }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
               // .navigationBarsPadding()
                .then(sizeModifier)
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .padding(16.dp)
                    ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .then(sizeModifier)
                    .height(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = { clearSelection() },
                    modifier = Modifier.size(24.dp),
                ) {
                    Image(
                        imageVector = Icons.Outlined.Close,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = "Close"
                    )
                }
                Text(
                    text = "Selected count",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            Row(
                modifier = Modifier
                    .then(sizeModifier)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    //.horizontalScroll(rememberScrollState())
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Share Component
                SelectionBarColumn(
                  //  selectedMedia = selectedMedia,
                    imageVector = Icons.Outlined.Share,
                    title = "Share"
                ) {
                   //  TODO Share
                }
//                val favoriteTitle =
//                    if (target == TARGET_FAVORITES) stringResource(id = R.string.remove_selected)
//                    else stringResource(id = R.string.favorites)
                // Favorite Component
                SelectionBarColumn(
                //    selectedMedia = selectedMedia,
                    imageVector = Icons.Outlined.FavoriteBorder,
                    title = "Favorite title"
                ) {
                    scope.launch {
                        //todo togle favorite
//                        handler.toggleFavorite(result = result, it)
                    }
                }
                // Move Component
                SelectionBarColumn(
                   // selectedMedia = selectedMedia,
                    imageVector = Icons.Outlined.DriveFileMove,
                    title = "Move"
                ) {
                    scope.launch {
                       // moveSheetState.show()
                    }
                }
                // Trash Component
                SelectionBarColumn(
                   // selectedMedia = selectedMedia,
                    imageVector = Icons.Outlined.DeleteOutline,
                    title = "Trash"
                ) {
                    scope.launch {
                      //  trashSheetState.show()
                    }
                }
            }
        }
    }


//    TrashDialog(
//        appBottomSheetState = trashSheetState,
//        data = selectedMedia,
//        action = TrashDialogAction.TRASH
//    ) {
//        handler.trashMedia(result, it, true)
//    }
}

@Composable
private fun SelectionBarColumn(
   // selectedMedia: SnapshotStateList<ImageFavoriteUi>,
    imageVector: ImageVector,
    title: String,
    onItemClick: (List<ImageFavoriteUi>) -> Unit
) {
    val tintColor = MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .height(80.dp)
            .width(80.dp)
            .clickable {
                //   onItemClick.invoke(selectedMedia)
            }
            .padding(top = 12.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = imageVector,
            colorFilter = ColorFilter.tint(tintColor),
            contentDescription = title,
            modifier = Modifier
                .height(32.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = title,
            modifier = Modifier,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium,
            color = tintColor,
            textAlign = TextAlign.Center
        )
    }
}