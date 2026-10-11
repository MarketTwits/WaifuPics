package com.markettwits.waifupics.gallery.items.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.IntSize

/** A fixed rail can reject a touch without hiding the date controls or photos below it. */
internal fun Modifier.scrollbarPointerInput(key: Any, block: suspend PointerInputScope.() -> Unit): Modifier =
    this then ScrollbarPointerElement(key, block)

// Sharing must also be declared on the rail's parent layout, where it overlaps the grid.
internal fun Modifier.shareScrollbarPointerInput(): Modifier = this then ScrollbarSharingElement

private object ScrollbarSharingElement : ModifierNodeElement<ScrollbarSharingNode>() {
    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = 0
    override fun create() = ScrollbarSharingNode()
    override fun update(node: ScrollbarSharingNode) = Unit
    override fun InspectorInfo.inspectableProperties() { name = "shareScrollbarPointerInput" }
}

private class ScrollbarSharingNode : Modifier.Node(), PointerInputModifierNode {
    override fun sharePointerInputWithSiblings(): Boolean = true
    override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) = Unit
    override fun onCancelPointerInput() = Unit
}

private data class ScrollbarPointerElement(val key: Any, val block: suspend PointerInputScope.() -> Unit) :
    ModifierNodeElement<ScrollbarPointerNode>() {
    override fun create() = ScrollbarPointerNode(key, block)
    override fun update(node: ScrollbarPointerNode) = node.update(key, block)
    override fun InspectorInfo.inspectableProperties() { name = "scrollbarPointerInput" }
}

private class ScrollbarPointerNode(
    private var key: Any,
    private var block: suspend PointerInputScope.() -> Unit,
) : DelegatingNode(), PointerInputModifierNode {
    private val input = delegate(SuspendingPointerInputModifierNode { block() })

    fun update(key: Any, block: suspend PointerInputScope.() -> Unit) {
        this.block = block
        if (this.key != key) {
            this.key = key
            input.resetPointerInputHandler()
        }
    }

    override fun sharePointerInputWithSiblings(): Boolean = true
    override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) =
        input.onPointerEvent(pointerEvent, pass, bounds)
    override fun onCancelPointerInput() = input.onCancelPointerInput()
}
