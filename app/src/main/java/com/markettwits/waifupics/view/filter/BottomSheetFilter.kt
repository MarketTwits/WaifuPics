package com.markettwits.waifupics.view.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.waifupics.R
import com.markettwits.waifupics.base.BaseDivider
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.theame.theme.LightPink
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

@Composable
@Preview
private fun BottomSheetPreview() {
    WaifuPicsTheme {
        BottomSheetFilter(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(modifier: Modifier = Modifier) {

    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(LocalViewModelStoreOwner.current!!, ImageViewModel::class.java)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val filterState = viewModel.filter.observeAsState()

    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .animateContentSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        FilterHeader(onClick = {
            openBottomSheet = !openBottomSheet
        }, isOpened = openBottomSheet)
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Column {
                    BaseDivider()
                    FilterBody(filterState = filterState.value!!){
                        viewModel.filter(it)
                    }
                    FilterHeader(
                        onClick = { openBottomSheet = !openBottomSheet },
                        isOpened = openBottomSheet
                    )
                }
            }
        }
    }
}

@Composable
fun FilterHeader(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isOpened: Boolean,
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Filter",
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 16.sp
        )
        Icon(
            imageVector = if (!isOpened) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
fun FilterBody(
    modifier: Modifier = Modifier,
    filterState : List<FilterItem>,
    selectedItem : (FilterItem) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Age rating",
            color = LightPink,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 12.sp
        )
        LazyColumn(
            //todo
            modifier = Modifier.height(250.dp),
            userScrollEnabled = false
        ) {
            items(filterState) {
                FilterPosition(item = it){
                    selectedItem.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun FilterPosition(item: FilterItem, onClick: (FilterItem) -> Unit) {
    val checkedState = rememberSaveable{
        mutableStateOf(item.checked)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                checkedState.value = !checkedState.value
                onClick(item.copy(checked = checkedState.value))
            },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = !checkedState.value })
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp
        )
    }
}

private fun filterCollections() = listOf(
    FilterItem(1, "Safe for work", "sfw"),
    FilterItem(2, "Questionable", "questionable"),
    FilterItem(3, "Suggestive", "suggestive"),
    FilterItem(4,"Borderline", "borderline"),
    FilterItem(5,"Explicit (18+)", "explicit")
)