package com.asuprojects.kotlincustomcomponents.prototypes.expandableitem

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class CheckableItemAdapter(
    var items: MutableList<CheckableItem>
) : RecyclerView.Adapter<CheckableItemAdapter.CheckableItemViewHolder>() {

    val views: MutableList<ViewGroup> = mutableListOf()
    private var onCheckItemListener: OnCheckItemListener? = null

    fun setOnCheckItemListener(listener: OnCheckItemListener){
        this.onCheckItemListener = listener
    }

    fun addItem(checkableItem: CheckableItem) {
        items.add(checkableItem)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        //items.remove(checkableItem)
        items.removeAt(position)
        notifyItemRemoved(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckableItemViewHolder {
        Log.i("TESTE", "CHECKABLE_ADAPTER onCreateViewHolder called...")
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_checkable_item, parent, false)
        return CheckableItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CheckableItemViewHolder, position: Int) {

        Log.i("TESTE", "CHECKABLE_ADAPTER onBindViewHolder on position: $position")
        val checkableItem = items[position]

        holder.name.text = checkableItem.name
        if(checkableItem.withCheckbox){
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                onCheckItemListener?.onCheckItem(buttonView, holder.adapterPosition, isChecked)
            }
            holder.checkableContainer.setOnClickListener {
                onCheckItemListener?.onClickItem(it, holder.adapterPosition)
            }
        }else{
            //holder.name.textAlignment = View.TEXT_ALIGNMENT_CENTER
            holder.checkBox.visibility = INVISIBLE
            holder.checkableContainer.setOnClickListener {
                onCheckItemListener?.onClickItem(it, holder.adapterPosition)
            }
        }
//        holder.checkableContainer.measure(
//            View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        Log.i("TESTE", "CHECKABLE_ADAPTER height: ${holder.checkableContainer.measuredHeight}")
        views.add(holder.checkableContainer)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CheckableItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkableContainer: ConstraintLayout =
            view.findViewById(R.id.vh_checkable_item_container)
        val checkBox: AppCompatCheckBox = view.findViewById(R.id.vh_checkable_item_checkbox)
        val name: AppCompatTextView = view.findViewById(R.id.vh_checkable_item_name)
    }

    interface OnCheckItemListener {
        fun onClickItem(view: View, position: Int)
        fun onCheckItem(view: View, position: Int, checked: Boolean)
    }
}