package com.markettwits.presentation

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavigationViewModel() : ViewModel() {

    fun toggleMenu(coroutineScope : CoroutineScope, drawerState: DrawerState){
        coroutineScope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }
}
