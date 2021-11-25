package com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.DisplayUtil

class CardScaleConstant {
    companion object {
        const val PAGER_PADDING = 25
        const val PAGER_MARGIN = 25
    }
}

class CardScaleHelper {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContext: Context
    private val mScale = 0.8f //The scale of the card on both sides

    private var mCardWidth: Int = 0 //Card width
    private var mCurrentItemPos: Int = 0 //The current position of the card
    private var mCurrentItemOffset: Int = 0 //Current offset

    private val pagerSnapHelper = PagerSnapHelper()

    fun attachToRecyclerView(recyclerView: RecyclerView){
        this.mRecyclerView = recyclerView
        this.mContext = recyclerView.context

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // dx>0 means slide right, dx<0 means slide left, dy<0 means slide up, dy>0 means slide down
                if (dx != 0) {
                    mCurrentItemOffset += dx;

                    //Calculate the current position
                    mCurrentItemPos = Math.round(mCurrentItemOffset * 1.0f / mCardWidth);

                    changedCardSize();
                }

            }
        })

        mRecyclerView.post(Runnable(){
            // Find the card width
            mCardWidth = mRecyclerView.width - DisplayUtil.dip2px(mContext, (2 * (CardScaleConstant.PAGER_PADDING + CardScaleConstant.PAGER_MARGIN)).toFloat());
            mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
            changedCardSize();

        })

        pagerSnapHelper.attachToRecyclerView(mRecyclerView)
    }

    private fun changedCardSize() {
        // Find the current item sliding offset, its absolute value changes from small to large and then becomes smaller
        //The change range is [0,mCardWidth/2],[-mCardWidth/2,0]

        // Find the current item sliding offset, its absolute value changes from small to large and then becomes smaller
        //The change range is [0,mCardWidth/2],[-mCardWidth/2,0]
        val offset = mCurrentItemOffset - mCurrentItemPos * mCardWidth
        // Find the zoom percentage, the minimum value is 0.0001, the maximum value is 1/2, the percentage change is from small to large, and then to small
        // Find the zoom percentage, the minimum value is 0.0001, the maximum value is 1/2, the percentage change is from small to large, and then to small
        val percent =
            Math.max(Math.abs(offset) * 1.0 / mCardWidth, 0.0001).toFloat()

        var leftView: View? = null
        val currentView: View
        var rightView: View? = null
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos - 1)
        }
        currentView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos)!!
        if (mCurrentItemPos < mRecyclerView.adapter!!.itemCount - 1) {
            rightView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos + 1)
        }

        if (leftView != null) {
            leftView.scaleY = (1 - mScale) * percent + mScale
        }
        if (currentView != null) {
            currentView.scaleY = (mScale - 1) * percent + 1
        }
        if (rightView != null) {
        }

    }
}