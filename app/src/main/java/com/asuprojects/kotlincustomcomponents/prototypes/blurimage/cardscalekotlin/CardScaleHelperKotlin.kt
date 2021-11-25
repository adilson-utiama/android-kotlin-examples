package com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.DisplayUtil

class CardScaleHelperKotlin {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContext: Context

    private var mScale: Float = 0.9f
    private var mPagePadding: Int = 15
    private var mShowLeftCardWidth: Int = 15

    private var mCardWidth: Int = 0
    private var mOnePageWidth: Int = 0
    private var mCardGalleryWidth: Int = 0

    private var mCurrentItemPos: Int = 0
    private var mCurrentItemOffSet: Int = 0

    private val mLinearSnapHelperKotlin = CardLinearSnapHelperKotlin()

    fun attachToRecyclerView(recyclerView: RecyclerView){
        this.mRecyclerView = recyclerView
        this.mContext = mRecyclerView.context

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelperKotlin.mNoNeedToScroll = mCurrentItemOffSet == 0 ||
                            mCurrentItemOffSet == getDestItemOffset(mRecyclerView.adapter!!.itemCount - 1)
                } else {
                    mLinearSnapHelperKotlin.mNoNeedToScroll = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx != 0) {
                    mCurrentItemOffSet += dx
                    computeCurrentItemPos()
                    onScrolledChangedCallback()
                }
            }
        })

        initWidth()
        mLinearSnapHelperKotlin.attachToRecyclerView(mRecyclerView)
    }

    private fun initWidth() {
        mRecyclerView.post {
            mCardGalleryWidth = mRecyclerView.width
            mCardWidth = mCardGalleryWidth - DisplayUtil.dip2px(
                mContext,
                2 * (mPagePadding + mShowLeftCardWidth).toFloat()
            )
            mOnePageWidth = mCardWidth
            mRecyclerView.smoothScrollToPosition(mCurrentItemPos)
            onScrolledChangedCallback()
        }
    }

    private fun onScrolledChangedCallback() {
        val offset: Int = mCurrentItemOffSet - mCurrentItemPos * mOnePageWidth
        val percent =
            Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001).toFloat()

        //LogUtils.d(String.format("offset=%s, percent=%s", offset, percent));

        //LogUtils.d(String.format("offset=%s, percent=%s", offset, percent));
        var leftView: View? = null
        val currentView: View?
        var rightView: View? = null
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos - 1)
        }
        currentView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos)
        if (mCurrentItemPos < mRecyclerView.adapter!!.itemCount - 1) {
            rightView = mRecyclerView.layoutManager!!.findViewByPosition(mCurrentItemPos + 1)
        }

        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.scaleY = (1 - mScale) * percent + mScale
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.scaleY = (mScale - 1) * percent + 1
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.scaleY = (1 - mScale) * percent + mScale
        }
    }

    private fun computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return
        var pageChanged = false
        if (Math.abs(mCurrentItemOffSet - mCurrentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true
        }
        if (pageChanged) {
            val tempPos = mCurrentItemPos
            mCurrentItemPos = mCurrentItemOffSet / mOnePageWidth
        }
    }

    private fun getDestItemOffset(destPos: Int): Int {
        return mOnePageWidth * destPos
    }

    fun setCurrentItemPos(currentItemPos: Int) {
        this.mCurrentItemPos = currentItemPos
    }

    fun getCurrentItemPos(): Int {
        return mCurrentItemPos
    }

    fun setScale(scale: Float) {
        this.mScale = scale
    }

    fun setPagePadding(pagePadding: Int) {
        this.mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        this.mShowLeftCardWidth = showLeftCardWidth
    }
}