package com.markettwits.waifupics.gallery.items.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import com.markettwits.waifupics.gallery.items.model.GalleryJustifiedRow
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi

/** Use the precomputed physical bounds verbatim; dp rounding must not move the scrollbar. */
@Composable
internal fun GalleryJustifiedRowContent(row: GalleryJustifiedRow,
    photo: @Composable (ImageFavoriteUi.Base, Modifier) -> Unit) {
    Layout(content = {
        row.cells.forEach { cell -> key(cell.image.id) { photo(cell.image, Modifier) } }
    }) { children, constraints ->
        val places = children.mapIndexed { index, child ->
            child.measure(Constraints.fixed(row.cells[index].width, row.height))
        }
        layout(constraints.maxWidth, row.height) {
            // Photo order and hit geometry stay left-to-right, as in the reference gallery.
            places.forEachIndexed { index, place -> place.place(row.cells[index].left, 0) }
        }
    }
}
