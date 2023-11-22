package com.markettwits.presentation.detail.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ShareButton(
  //  media: Media,
    followTheme: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    BottomBarColumn(
       // currentMedia = media,
        imageVector = Icons.Outlined.Share,
        followTheme = followTheme,
        title = "Share"
    ) {
        scope.launch {
           // context.shareMedia(media = it)
        }
    }
}

@Composable
 fun FavoriteButton(
  //  media: Media,
    //handler: MediaHandleUseCase,
    followTheme: Boolean = false
) {
    val scope = rememberCoroutineScope()
 //   val result = rememberActivityResult()
//    val favoriteIcon by remember(media) {
//        mutableStateOf(
//            if (media.isFavorite)
//                Icons.Filled.Favorite
//            else Icons.Outlined.FavoriteBorder
//        )
//    }
        BottomBarColumn(
         //   currentMedia = media,
            imageVector = Icons.Default.Favorite,
            followTheme = followTheme,
            title = "favorites"
        ) {
            scope.launch {
              //  handler.toggleFavorite(result = result, arrayListOf(it), it.favorite != 1)
            }
        }

}

@Composable
 fun EditButton(
 //   media: Media,
    followTheme: Boolean = false
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    BottomBarColumn(
      //  currentMedia = media,
        imageVector = Icons.Outlined.Edit,
        followTheme = followTheme,
        title = "Edit"
    ) {
      //  scope.launch { context.launchEditIntent(it) }
    }
}

@Composable
 fun OpenAsButton(
    // media: Media,
    followTheme: Boolean = false
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    BottomBarColumn(
        // currentMedia = media,
        imageVector = Icons.Default.OpenInNew,
        followTheme = followTheme,
        title = "use as"
    ) {
        //scope.launch { context.launchUseAsIntent(it) }
    }
}

@Composable
fun TrashButton(
    index: Int,
    followTheme: Boolean = false,
    onDeleteMedia: ((Int) -> Unit)?
) {
    val scope = rememberCoroutineScope()

    BottomBarColumn(
        //   currentMedia = media,
        imageVector = Icons.Outlined.DeleteOutline,
        followTheme = followTheme,
        title = "Trash"
    ) {
//        scope.launch {
//            state.show()
//        }
    }

//    TrashDialog(
//        appBottomSheetState = state,
//        data = listOf(media),
//        action = TrashDialogAction.TRASH
//    ) {
//        handler.trashMedia(result, it, true)
//        onDeleteMedia?.invoke(index)
//    }
}

@Composable
fun BottomBarColumn(
    // currentMedia: Media?,
    imageVector: ImageVector,
    title: String,
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    val tintColor = if (followTheme) MaterialTheme.colorScheme.onSurface else Color.White
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .defaultMinSize(
                minWidth = 90.dp,
                minHeight = 80.dp
            )
            .clickable {

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