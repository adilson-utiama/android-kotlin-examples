package com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager

import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class PagerSnapHelperVerbose(val recyclerView: RecyclerView, private val externalListener: MyPagerStateListener)
    : PagerSnapHelper(), ViewTreeObserver.OnGlobalLayoutListener{

    var lastPage = RecyclerView.NO_POSITION

    init {
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        if(position != RecyclerView.NO_POSITION){
            notifyNewPageIfNeeded(position)
            recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val view = super.findSnapView(layoutManager)
        view?.let {
            notifyNewPageIfNeeded(recyclerView.getChildAdapterPosition(it))
        }
        Log.i("TESTE", ">>> findSnapView.... ${view == null}")
        return view
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        if(position < recyclerView.adapter!!.itemCount){
            notifyNewPageIfNeeded(position)
        }
        return position
    }

    private fun notifyNewPageIfNeeded(page: Int) {
        if(page != lastPage){
            this.externalListener.onPageSelected(page)
            lastPage = page
        }
    }

}
