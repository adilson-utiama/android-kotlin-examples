package com.asuprojects.kotlincustomcomponents.fragments.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.utils.OnSwipeAction


interface OnClickItem{
    fun onClickSimpleItem(view: View, position: Int)
}

class SimpleSwipeItemAdapter(val items: MutableList<String>)
    : RecyclerView.Adapter<SimpleSwipeItemAdapter.SimpleItemViewHolder>(), OnSwipeAction{

    private var onClickItem: OnClickItem? = null

    fun setOnClickItem(listener: OnClickItem){
        this.onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_simple_swipe_item, parent, false)
        return SimpleItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
        val text = items[position]
        holder.simpleItemText.text = text
        holder.container.setOnClickListener {
            onClickItem?.onClickSimpleItem(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SimpleItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val container: ConstraintLayout = view.findViewById(R.id.vh_simple_item_container)
        val simpleItemText: AppCompatTextView = view.findViewById(R.id.vh_simple_item_text)
    }

    override fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    override fun editItem(position: Int) {

    }

    override fun restoreItem(obj: Any, position: Int) {
        items.add(position, obj as String)
        notifyItemInserted(position)
    }
}