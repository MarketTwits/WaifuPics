package com.markettwits.presentation

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavigationViewModel(
    //private val menuState : NavigationMenuStateCommunication
    private val handleNavigation: HandleNavigation
) : ViewModel() {

    fun toggleMenu(coroutineScope : CoroutineScope, drawerState: DrawerState){
        coroutineScope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }
    fun handleNavigation() = handleNavigation
    fun updateSelectedItemInList(
        list: List<NavigationItem>,
        selectedItem: NavigationItem
    ): List<NavigationItem> {
        return list.map { item ->
            if (item == selectedItem) {
                item.copy(selected = true)
            } else {
                item.copy(selected = false)
            }
        }
    }
     fun init() = listOf(
         NavigationItem.Home(),
         NavigationItem.Favorite(),
         NavigationItem.About()
    )
}
interface NavigationMenuStateCommunication : StateCommunication.Mutable<Boolean>{
    class Base : StateCommunication.Abstract<Boolean>(false), NavigationMenuStateCommunication
}