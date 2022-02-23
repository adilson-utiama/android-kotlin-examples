package com.asuprojects.kotlincustomcomponents.screens.menupopup.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.screens.menupopup.adapter.GridMenuAdapter
import com.asuprojects.kotlincustomcomponents.screens.menupopup.widget.decoration.GridMenuDecoration

class MenuRecyclerView : RecyclerView {

    private val spanCount = 3

    private val manager = GridLayoutManager(context, spanCount)
    private val gridMenuAdapter = GridMenuAdapter()


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    init {
        setHasFixedSize(true)
        layoutManager = manager
        adapter = gridMenuAdapter
        addItemDecoration(GridMenuDecoration())
    }

    fun addMenuClickListener(listener: GridMenuAdapter.GridMenuListener) {
        gridMenuAdapter.listener = listener
    }

}