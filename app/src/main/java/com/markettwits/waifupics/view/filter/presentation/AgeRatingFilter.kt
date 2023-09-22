package com.markettwits.waifupics.view.filter.presentation


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
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.ActivityViewModel
import com.markettwits.waifupics.R
import com.markettwits.waifupics.base.BaseDivider
import com.markettwits.waifupics.theame.theme.LightPink
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

@Composable
@Preview
private fun BottomSheetPreview() {
    WaifuPicsTheme {
        BottomSheetFilter()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(modifier: Modifier = Modifier) {
    val viewModel: AgeRatingFilterViewModel = ActivityViewModel()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val filterState by viewModel.state().collectAsState()
    AgeFilterBox(modifier = modifier) {
        FilterHeader(
            onClick = { viewModel.toggle() },
            isOpened = filterState.isOpened
        )
        if (filterState.isOpened) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.toggle() },
                sheetState = bottomSheetState,
            ) {
                BottomScreenContent(
                    filterState = filterState,
                    toggleScreen = { viewModel.toggle() },
                    isOpened = filterState.isOpened
                ) {
                    viewModel.filter(it)
                }
            }
        }
    }
}

@Composable
fun BottomScreenContent(
    filterState: FilterState,
    toggleScreen: () -> Unit,
    isOpened: Boolean,
    onItemClick: (FilterItem) -> Unit
) = Column {
    BaseDivider()
    FilterBody(filterState = filterState) {
        onItemClick(it)
    }
    FilterHeader(
        onClick = { toggleScreen() },
        isOpened = isOpened
    )
}


@Composable
fun AgeFilterBox(
    modifier: Modifier,
    content: @Composable () -> Unit,
) = Box(
    modifier = modifier
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colorScheme.secondary)
        .fillMaxWidth()
        .animateContentSize(),
    contentAlignment = Alignment.CenterEnd
) {
    content()
}


@Composable
fun FilterHeader(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isOpened: Boolean,
) =
    Row(
        modifier = modifier
            .clickable { onClick() }
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxWidth()
            .padding(10.dp),
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


@Composable
fun FilterBody(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    selectedItem: (FilterItem) -> Unit,
) = Column(
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
    Column(
        modifier = Modifier.height(250.dp),
    ) {
        filterState.filter.forEach {
            FilterPosition(
                canBeChecked = filterState.couldBeChecked(it),
                item = it
            ) {
                selectedItem.invoke(it)
            }
        }
    }
}


@Composable
private fun FilterPosition(
    canBeChecked: Boolean,
    item: FilterItem,
    onClick: (FilterItem) -> Unit,
) {
    var checkedState by rememberSaveable(item.id) {
        mutableStateOf(item.checked)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!canBeChecked) {
                    checkedState = !checkedState
                    onClick(item.copy(checked = checkedState))
                }
            },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { checkedState = !checkedState })
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp
        )
    }
}