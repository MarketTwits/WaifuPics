package com.markettwits.presentation

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface NavigationViewModel{

    fun toggleMenu(coroutineScope : CoroutineScope, drawerState: DrawerState)

    fun navigateTo(route: String)

    class Base(private val navigation : NavigationRouter) : NavigationViewModel, ViewModel(){
        override fun toggleMenu(coroutineScope : CoroutineScope, drawerState: DrawerState){
            coroutineScope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                } else {
                    drawerState.close()
                }
            }
        }

        override fun navigateTo(route: String) {
            navigation.navigateTo(route)
        }
    }
}
