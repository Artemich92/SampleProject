package com.sampleproject.utils.recycler

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

private const val ONE_ITEM = 1
private const val TWO_ITEMS = 2
private const val FIRST_ITEM_POSITION = 0

class ImageRecyclerItemDecorator(leftPadding: Int = 0, rightPadding: Int = 0) : RecyclerView.ItemDecoration() {
    private val recRight = Rect(0, 0, rightPadding, 0)
    private val rectHor = Rect(leftPadding, 0, rightPadding, 0)
    private val rectZero = Rect(0, 0, 0, 0)

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        val penultimatePosition = parent.adapter?.itemCount?.minus(2)
        val lastPosition = parent.adapter?.itemCount?.minus(1)
        when (parent.adapter?.itemCount) {
            ONE_ITEM or TWO_ITEMS -> outRect.set(rectHor)
            else ->
                when (itemPosition) {
                    FIRST_ITEM_POSITION -> outRect.set(rectHor)
                    penultimatePosition -> outRect.set(rectZero)
                    lastPosition -> outRect.set(rectHor)
                    else -> outRect.set(recRight)
                }
        }
    }
}
