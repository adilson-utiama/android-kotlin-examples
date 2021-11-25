package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

interface OnClickSimpleItemListener {
    fun onClickSimpleItem(view: View, position: Int)
}

class SimpleItemTextAdapter(val files: MutableList<String>)
    : RecyclerView.Adapter<SimpleItemTextAdapter.SimpleItemViewHolder>(){

    private var onClickSimpleItemListener: OnClickSimpleItemListener? = null

    fun setOnClickSimpleItemListener(listener: OnClickSimpleItemListener){
        this.onClickSimpleItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_simple_item, parent, false)
        return SimpleItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
        val file = files[position]
        holder.itemText.text = file
        holder.container.setOnClickListener {
            onClickSimpleItemListener?.onClickSimpleItem(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class SimpleItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val container: ConstraintLayout = view.findViewById(R.id.vh_container_simple_item)
        val itemText: AppCompatTextView = view.findViewById(R.id.vh_text_simple_item)
    }

}
