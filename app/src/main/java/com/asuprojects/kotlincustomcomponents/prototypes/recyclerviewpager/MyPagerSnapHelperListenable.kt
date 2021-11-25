package com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager

import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

data class VisiblePageState(
    var index: Int,
    var view: View,
    @Px var viewCenterX: Int,
    @Px var distanceToSettledPixels: Int,
    var distanceToSettled: Float
)

interface MyPagerStateListener {
    fun onPageScroll(pagesState: List<VisiblePageState>)
    fun onScrollStateChanged(state: MyPageScrollState)
    fun onPageSelected(index: Int)
}

open class MyPagerSnapHelperListenable(private val maxPages: Int = 3) {

    fun attachToRecyclerView(recyclerView: RecyclerView, listener: MyPagerStateListener){
        assertRecyclerViewSetUp(recyclerView)
        setUpSnapHelper(recyclerView, listener)
        setUpScrollListener(recyclerView, listener)
    }

    private fun setUpScrollListener(recyclerView: RecyclerView, listener: MyPagerStateListener) =
        PagerSnapScrollListener(recyclerView, listener, maxPages)


    private fun setUpSnapHelper(recyclerView: RecyclerView, listener: MyPagerStateListener) =
        PagerSnapHelperVerbose(recyclerView, listener).attachToRecyclerView(recyclerView)


    private fun assertRecyclerViewSetUp(recyclerView: RecyclerView) {
        if(recyclerView.layoutManager !is LinearLayoutManager){
            throw IllegalArgumentException("MyPagerSnapHelperListenable can only work with a LinearLayoutManager")
        }
        if((recyclerView.layoutManager as LinearLayoutManager).orientation != LinearLayoutManager.HORIZONTAL){
            throw IllegalArgumentException("MyPagerSnapHelperListenable can only work with a horizontal orientation")
        }
    }
}