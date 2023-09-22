package com.markettwits.waifupics.view.filter.presentation

import android.os.Bundle
import com.markettwits.core.wrappers.SaveAndRestoreState

interface SaveAndRestoreAgeRatingFilter {
    interface Save : SaveAndRestoreState.Save<FilterState>
    interface Restore : SaveAndRestoreState.Restore<FilterState>
    interface Mutable : Save, Restore
    class Base(
        bundle: Bundle?
    ) : SaveAndRestoreState.Abstract<FilterState>(
        bundle,
        KEY,
        FilterState::class.java
    ), Mutable

    companion object {
        private const val KEY = "SaveAndRestoreAgeFilterUiStateKey"
    }
}

