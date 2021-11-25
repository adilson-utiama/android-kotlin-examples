package com.asuprojects.kotlincustomcomponents.fragments.lists.gridlayoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AutoFitGridLayoutManager(val context: Context, columnWidth: Int)
    : GridLayoutManager(context, 1) {

    private var columnWidthChanged = true
    private var mColumnWidth: Int = 0

    init {
        //mColumnWidth = columnWidth
        setColumnWidth(columnWidth)
    }

    fun setColumnWidth(newColumnWidth: Int){
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth) {
            mColumnWidth = newColumnWidth
            columnWidthChanged = true
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (columnWidthChanged && mColumnWidth > 0) {
            val totalSpace = if (orientation == LinearLayoutManager.VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }
            val spanCount = Math.max(1, totalSpace / mColumnWidth)
            setSpanCount(spanCount)
            columnWidthChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}