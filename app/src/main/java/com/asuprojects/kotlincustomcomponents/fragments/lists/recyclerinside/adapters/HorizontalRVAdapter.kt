package com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models.HorizontalRVModel
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.viewholder_horizontal_item.view.*

class HorizontalRVAdapter(private var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsList: ArrayList<HorizontalRVModel> = ArrayList()
    private var mRowIndex = -1

    fun setColorsData(data: ArrayList<HorizontalRVModel>) {
        if (itemsList != data) {
            itemsList = data
            notifyDataSetChanged()
        }
    }

    fun setRowIndex(index: Int) {
        mRowIndex = index
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.viewholder_horizontal_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.item_image.setBackgroundColor(Color.parseColor(itemsList[position].color))
        holder.itemView.item_title.text = itemsList[position].name
        holder.itemView.setOnClickListener {
            Log.d(
                "TAG",
                "You pressed the item in the category row $mRowIndex and position $position"
            )

            ToastUtil.msg(mContext, "Selected Color: ${itemsList[position].name}")
        }

    }

}