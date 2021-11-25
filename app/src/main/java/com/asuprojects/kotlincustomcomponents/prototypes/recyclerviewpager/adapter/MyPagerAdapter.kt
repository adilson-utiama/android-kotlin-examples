package com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.custom.CircularProgressBar

data class PageModel(
    val bkgColor: Int
)

class MyPagerAdapter(
    private val mModels: List<PageModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var mInflater: LayoutInflater? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mInflater = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyPagerViewHolder(mInflater!!, parent)
    }

    override fun getItemCount() = mModels.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = mModels[position]
        (holder as MyPagerListener).bind(model)
    }
}

interface MyPagerListener {
    fun bind(model: PageModel)
    fun setRealtimeAttr(index: Int, centerXText: String, horizOffSetRate: Float, @Px horizOffSetPx: Int)
    fun onSelected()
}

class MyPagerViewHolder : RecyclerView.ViewHolder, MyPagerListener {

    private val mPageIndexText: AppCompatTextView

    private val mPositioningText: AppCompatTextView
    private val mProgressBar: CircularProgressBar

    constructor(inflater: LayoutInflater, parent: ViewGroup)
            : this(inflater.inflate(R.layout.viewholder_page_item_func, parent, false) as ViewGroup)

    constructor(listPageView: ViewGroup)
            : super(listPageView) {
        mPageIndexText = listPageView.findViewById(R.id.pageIndex)
        mPositioningText  = listPageView.findViewById(R.id.positioningText)
        mProgressBar = listPageView.findViewById(R.id.progressBar)
        mProgressBar.setProgressColor(Color.WHITE)
        mProgressBar.showProgressText(true)
        mProgressBar.useRoundedCorners(false)
    }

    override fun bind(model: PageModel) {
        val (bkgColor) = model
        itemView.setBackgroundColor(bkgColor)
    }

    override fun setRealtimeAttr(index: Int, centerXText: String, horizOffsetRate: Float, @Px horizOffsetPx: Int) {

        Log.i("TESTE", ">>> Page: $index, HorizRate: $horizOffsetRate, HorizPx: $horizOffsetPx")

        mPageIndexText.text = "Page #$index"
        mPositioningText.text = "$centerXText px ($horizOffsetPx px)"

        val color = if (horizOffsetRate > 1) Color.LTGRAY else Color.WHITE
        mProgressBar.setProgressColor(color)
        mProgressBar.setTextColor(color)
        mProgressBar.setProgress((horizOffsetRate * 100).toInt())
    }

    override fun onSelected() {
        val scaleXAnim = ObjectAnimator.ofFloat(mPageIndexText, View.SCALE_X, 0.75f, 1.8f, 1f, 0.8f, 1f)
        val scaleYAnim = ObjectAnimator.ofFloat(mPageIndexText, View.SCALE_Y, 0.75f, 1.8f, 1f, 0.8f, 1f)

        val animation = AnimatorSet()
        animation.playTogether(scaleXAnim, scaleYAnim)
        animation.duration = 600

        animation.start()
    }
}