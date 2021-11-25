package com.asuprojects.kotlincustomcomponents.fragments.lists.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.utils.SwipeAndDragDropHelper
import java.util.*

class SwipeDragDropItemAdapter(val lista: MutableList<String>, val touchHelper: SwipeAndDragDropHelper.StartDragListener)
    : RecyclerView.Adapter<SwipeDragDropItemAdapter.ItemViewHolder>(),
    SwipeAndDragDropHelper.OnSwipeDragDropAction {

    interface OnClickItem{
        fun onClickSWipeDragDropItem(view: View, position: Int)
    }

    private var onClickItem: OnClickItem? = null

    fun setOnClickItem(listener: OnClickItem){
        this.onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_swipe_drag_drop_item, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = lista[position]
        holder.texto.text = item
        holder.iconMoveItem.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                touchHelper.requestDrag(holder)
            }
            false
        }
        holder.rowView.setOnClickListener {
            onClickItem?.onClickSWipeDragDropItem(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun removeItem(position: Int) {
        lista.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, lista.size)
    }

    override fun editItem(position: Int) {

    }

    override fun restoreItem(obj: Any, position: Int) {
        lista.add(position, obj as String)
        notifyItemInserted(position)
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(lista, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(lista, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ItemViewHolder) {
        itemViewHolder.rowView.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(itemViewHolder: ItemViewHolder) {
        itemViewHolder.rowView.setBackgroundColor(Color.WHITE)
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val texto: AppCompatTextView = view.findViewById(R.id.vh_nome_item)
        val rowView: ConstraintLayout = view.findViewById(R.id.vh_row_view)
        val iconMoveItem: AppCompatImageView = view.findViewById(R.id.vh_icon_move_item)
    }


}