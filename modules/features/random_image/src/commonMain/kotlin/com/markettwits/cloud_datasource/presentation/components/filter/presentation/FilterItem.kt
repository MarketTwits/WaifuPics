package com.markettwits.cloud_datasource.presentation.components.filter.presentation

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
data class FilterItem(
    val id : Int,
    val title : StringResource,
    val value : String,
    val checked : Boolean = false,
) {
    fun checked(isChecked : Boolean) = this.copy(checked = !isChecked)
}

