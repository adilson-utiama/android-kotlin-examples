package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class TransitionItemAdapter(val products: MutableList<Product>)
    : RecyclerView.Adapter<TransitionItemAdapter.TransitionItemViewHolder>(){

    private var onClickProduct: OnClickProduct? = null

    fun setOnClickProduct(listener: OnClickProduct){
        this.onClickProduct = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransitionItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_transition_item, parent, false)
        return TransitionItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TransitionItemViewHolder, position: Int) {
        val product = products[position]
        holder.image.setImageResource(product.resourceId)
        holder.image.setOnClickListener{
            onClickProduct?.onSelectProduct(it, holder.adapterPosition)
        }
        holder.title.text = product.title
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class TransitionItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: AppCompatImageView = view.findViewById(R.id.vh_transition_item_image)
        val title: AppCompatTextView = view.findViewById(R.id.vh_transition_title)
    }

    interface OnClickProduct {
        fun onSelectProduct(view: View, position: Int)
    }
}