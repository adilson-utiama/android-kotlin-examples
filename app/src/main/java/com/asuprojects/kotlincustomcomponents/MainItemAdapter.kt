package com.asuprojects.kotlincustomcomponents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

interface OnClickItem{
    fun onClickMainItem(view: View, position: Int)
}

class MainItemAdapter(val items: MutableList<MainItem>)
    : RecyclerView.Adapter<MainItemAdapter.MainItemViewHolder>(){

    private var onClickItem: OnClickItem? = null

    fun setOnClickItem(listener: OnClickItem){
        this.onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_main_item, parent,false)
        return MainItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        val mainItem = items[position]
        holder.mainItemDescricao.text = mainItem.descricao
        holder.mainItem.setOnClickListener {
            onClickItem?.onClickMainItem(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mainItemDescricao: AppCompatTextView = view.findViewById(R.id.vh_main_item_descricao)
        val mainItem: ConstraintLayout = view.findViewById(R.id.vh_main_item)
    }


}