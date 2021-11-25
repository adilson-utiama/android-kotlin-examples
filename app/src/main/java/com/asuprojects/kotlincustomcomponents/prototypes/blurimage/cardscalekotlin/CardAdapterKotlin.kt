package com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class CardAdapterKotlin(
    val mList: List<Int>
) : RecyclerView.Adapter<CardAdapterKotlin.CardViewHolder>() {

    private var onClickImageListener: OnClickImageListener? = null
    private val mCardAdapterHelperKotlin: CardAdapterHelperKotlin = CardAdapterHelperKotlin()

    fun setOnclickImageListener(listener: OnClickImageListener){
        this.onClickImageListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_card_scale_item, parent, false)
        mCardAdapterHelperKotlin.onCreateViewHolder(parent, inflatedView)
        return CardViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        mCardAdapterHelperKotlin.onBindViewHolder(holder.itemView, position, itemCount)
        val imageRes = mList[position]
        holder.mImageView.setImageResource(imageRes)
        holder.mImageView.setOnClickListener {
            onClickImageListener?.onCLickImage(it, imageRes)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class CardViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val mImageView: AppCompatImageView = view.findViewById(R.id.card_scale_image_view)
    }

    interface OnClickImageListener {
        fun onCLickImage(view: View, imageResId: Int)
    }
}
