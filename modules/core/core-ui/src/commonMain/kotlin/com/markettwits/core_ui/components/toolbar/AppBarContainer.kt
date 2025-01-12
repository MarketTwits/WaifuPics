
package com.markettwits.core_ui.components.toolbar

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import kotlin.math.max

interface AppbarContainerScope {
	fun Modifier.appBarBody(): Modifier
}

internal class AppbarContainerScopeImpl(
	private val nestedScrollConnection: NestedScrollConnection
) : AppbarContainerScope {
	override fun Modifier.appBarBody(): Modifier {
		return this
			.then(AppBarBodyMarkerModifier)
			.nestedScroll(nestedScrollConnection)
	}
}

private object AppBarBodyMarkerModifier : ParentDataModifier {
	override fun Density.modifyParentData(parentData: Any?): Any {
		return AppBarBodyMarker
	}
}

private object AppBarBodyMarker

private class AppbarMeasurePolicy(
	private val scrollStrategy: ScrollStrategy,
	private val toolbarState: CollapsingToolbarState,
	private val offsetY: State<Int>
) : MeasurePolicy {
	override fun MeasureScope.measure(
		measurables: List<Measurable>,
		constraints: Constraints
	): MeasureResult {
		var width = 0
		var height = 0

		var toolbarPlaceable: Placeable? = null

		val nonToolbars = measurables.filter {
			val data = it.parentData
			if (data != AppBarBodyMarker) {
				if (toolbarPlaceable != null)
					throw IllegalStateException("There cannot exist multiple toolbars under single parent")

				val placeable = it.measure(
					constraints.copy(
						minWidth = 0,
						minHeight = 0
					)
				)
				width = max(width, placeable.width)
				height = max(height, placeable.height)

				toolbarPlaceable = placeable

				false
			} else {
				true
			}
		}

		val placeables = nonToolbars.map { measurable ->
			val childConstraints = if (scrollStrategy == ScrollStrategy.ExitUntilCollapsed) {
				constraints.copy(
					minWidth = 0,
					minHeight = 0,
					maxHeight = max(0, constraints.maxHeight - toolbarState.minHeight)
				)
			} else {
				constraints.copy(
					minWidth = 0,
					minHeight = 0
				)
			}

			val placeable = measurable.measure(childConstraints)

			width = max(width, placeable.width)
			height = max(height, placeable.height)

			placeable
		}

		height += (toolbarPlaceable?.height ?: 0)

		return layout(
			width.coerceIn(constraints.minWidth, constraints.maxWidth),
			height.coerceIn(constraints.minHeight, constraints.maxHeight)
		) {
			toolbarPlaceable?.place(x = 0, y = offsetY.value)

			placeables.forEach { placeable ->
				placeable.place(
					x = 0,
					y = offsetY.value + (toolbarPlaceable?.height ?: 0)
				)
			}
		}
	}
}
