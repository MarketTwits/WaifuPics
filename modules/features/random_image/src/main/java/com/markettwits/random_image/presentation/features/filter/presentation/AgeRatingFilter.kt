package com.markettwits.random_image.presentation.features.filter.presentation
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.R
import com.markettwits.core_ui.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.base.BaseDivider
import com.markettwits.waifupics.filter.presentation.FilterState
import com.markettwits.core_ui.theame.theme.LightPink

@Composable
@Preview
private fun BottomSheetPreview() {
    WaifuPicsTheme {
        BottomSheetFilter()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(
    modifier: Modifier = Modifier
) {

    val viewModel: AgeRatingFilterViewModel = ApplicationViewModel()
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
                ) {
                    viewModel.checked(it)
                }
            }
        }
    }
}

@Composable
fun BottomScreenContent(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    toggleScreen: () -> Unit,
    onItemClick: (FilterItem) -> Unit
) = Column {
    BaseDivider()
    FilterBody(filterState = filterState) {
        onItemClick(it)
    }
    FilterHeader(
        onClick = { toggleScreen() },
        isOpened = filterState.isOpened
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!canBeChecked) onClick(item)
            },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Checkbox(
            checked = item.checked,
            onCheckedChange = { onClick(item) })
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            fontSize = 14.sp
        )
    }
}