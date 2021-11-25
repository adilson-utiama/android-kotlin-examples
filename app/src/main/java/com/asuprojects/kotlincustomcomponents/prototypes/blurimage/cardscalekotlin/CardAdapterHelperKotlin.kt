package com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CardAdapterHelperKotlin {

    private var mPagePadding: Int = 15
    private var mShowLefCardWidth: Int = 15

    fun onCreateViewHolder(parent: ViewGroup, itemView: View){
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        lp.width = parent.width - DisplayUtilKotlin.dip2px(itemView.context, (2 * (mPagePadding + mShowLefCardWidth)).toFloat())
        itemView.layoutParams = lp
    }

    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int) {
        val padding = DisplayUtilKotlin.dip2px(itemView.context, mPagePadding.toFloat())
        itemView.setPadding(padding, 0, padding, 0)
        val leftMargin = if(position == 0) (padding + DisplayUtilKotlin.dip2px(itemView.context, mShowLefCardWidth.toFloat())) else 0
        val rightMargin = if(position == itemCount - 1) (padding + DisplayUtilKotlin.dip2px(itemView.context, mShowLefCardWidth.toFloat())) else 0
        setViewMargin(itemView, leftMargin, 0, rightMargin, 0)
    }

    private fun setViewMargin(itemView: View, leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int) {
        val lp = itemView.layoutParams as ViewGroup.MarginLayoutParams
        if(lp.leftMargin != leftMargin || lp.topMargin != topMargin || lp.rightMargin != rightMargin || lp.bottomMargin != bottomMargin) {
            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            itemView.layoutParams = lp
        }
    }

    fun setPagePadding(pagePadding: Int) {
        mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        mShowLefCardWidth = showLeftCardWidth
    }
}