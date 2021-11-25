package com.asuprojects.kotlincustomcomponents.fragments.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class SimpleItemMenuAdapter(val items: MutableList<String>)
    : RecyclerView.Adapter<SimpleItemMenuAdapter.SimpleItemMenuViewHolder>(){

    private var onClickItemMenuListener: OnClickItemMenuListener? = null

    fun setOnClickItemMenuListener(listener: OnClickItemMenuListener){
        this.onClickItemMenuListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_simple_item_popup_menu, parent , false)
        return SimpleItemMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleItemMenuViewHolder, position: Int) {
        val texto = items[position]
        holder.itemText.text = texto
        holder.itemContainer.setOnClickListener {
            onClickItemMenuListener?.onClickItem(it, holder.adapterPosition)
        }
        holder.iconMenu.setOnClickListener {
            onClickItemMenuListener?.onClickMenu(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SimpleItemMenuViewHolder(view: View): RecyclerView.ViewHolder(view){
        val itemText: AppCompatTextView = view.findViewById(R.id.vh_item_descricao)
        val itemContainer: ConstraintLayout = view.findViewById(R.id.vh_item_popup_menu_container)
        val iconMenu: AppCompatImageView = view.findViewById(R.id.vh_item_icon_menu)
    }

    interface OnClickItemMenuListener{
        fun onClickItem(view: View, position: Int)
        fun onClickMenu(view: View, position: Int)
    }
}