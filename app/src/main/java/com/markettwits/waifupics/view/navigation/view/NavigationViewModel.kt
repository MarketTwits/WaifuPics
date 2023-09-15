package com.markettwits.waifupics.view.navigation.view

import androidx.lifecycle.ViewModel
import com.markettwits.waifupics.view.navigation.model.NavigationItem

class NavigationViewModel : ViewModel() {
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