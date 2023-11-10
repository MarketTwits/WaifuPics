package com.markettwits.presentation.detail.topbar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ItemDropDownMenu(setAs : () -> Unit, info : () -> Unit, saveToGallery : () -> Unit) {
    Box() {
        var expanded by remember { mutableStateOf(false) }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, "", tint = MaterialTheme.colorScheme.onBackground)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { setAs();expanded = false }, text = { Text(text = "Set as") })
            DropdownMenuItem(onClick = { expanded = false }, text = { Text(text = "Hide") })
            DropdownMenuItem(onClick = { info();expanded = false }, text = { Text(text = "Info") })
            DropdownMenuItem(onClick = { saveToGallery();expanded = false }, text = { Text(text = "Save to gallery") })
        }
    }
}
