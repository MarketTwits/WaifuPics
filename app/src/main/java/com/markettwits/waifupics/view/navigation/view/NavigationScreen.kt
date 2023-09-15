package com.markettwits.waifupics.view.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import com.markettwits.waifupics.R
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.extensions.noRippleClickable
import com.markettwits.waifupics.view.navigation.nav_grapth.NavGraph
import com.markettwits.waifupics.view.navigation.model.NavigationItem
import com.markettwits.waifupics.view.navigation.nav_grapth.rememberNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), NavigationViewModel::class.java
    )

    val navigationState = rememberNavigationState()
    val navBackStackEntry =
        navigationState.navHostController.currentBackStackEntryAsState()

    val selectedItemSaveble = rememberSaveable {
        mutableStateOf(viewModel.init())
    }

    Scaffold(bottomBar = {
        NavigationPanel(
            list = selectedItemSaveble.value,
            navItemClick = { selectedItem ->
                if (!selectedItem.isSelected) {
                    navigationState.navigateTo(selectedItem.screen.route())
                }
                selectedItemSaveble.value =
                    viewModel.updateSelectedItemInList(selectedItemSaveble.value, selectedItem)
            }
        )
    }) {
        NavGraph(
            paddingValues = it,
            navigationState = navigationState
        )
    }
}

@Composable
fun NavigationPanel(
    list: List<NavigationItem>,
    navItemClick: (NavigationItem) -> Unit
) {
    Column(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
        ,
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
                        .noRippleClickable {
                            navItemClick(it)
                        }
                        .weight(1f),
                    text = stringResource(id = it.title),
                    color = if (!it.isSelected) Color.Gray else MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
