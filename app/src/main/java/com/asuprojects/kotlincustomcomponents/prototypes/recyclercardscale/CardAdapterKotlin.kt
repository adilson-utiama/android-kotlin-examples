package com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.CardAdapterHelper

class CardAdapterKotlin(
    val mList: List<Int>
) : RecyclerView.Adapter<CardAdapterKotlin.CardViewHolder>() {

    private val mCardAdapterHelper = CardAdapterHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_card_scale_item, parent, false)
        mCardAdapterHelper.onCreateViewHolder(parent, itemView)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, itemCount)
        holder.mImageView.setImageResource(mList[position])
        holder.mImageView.setOnClickListener {
            Toast.makeText(
                holder.mImageView.context,
                "" + position,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: AppCompatImageView = itemView.findViewById(R.id.card_scale_image_view)

    }
}