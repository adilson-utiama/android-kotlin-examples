package com.asuprojects.kotlincustomcomponents.fragments.lists.gridlayoutmanager

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.viewholder_recycler_gridlayout_item.view.*

class RecyclerGridLayoutAdaper(val values: List<DataModel>, listener: ItemListener)
    : RecyclerView.Adapter<RecyclerGridLayoutAdaper.GridLayoutViewHolder>(){

    private var mListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridLayoutViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_recycler_gridlayout_item, parent, false)
        return GridLayoutViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: GridLayoutViewHolder, position: Int) {
        val dataModel = values[position]
        holder.setData(dataModel)
        holder.container.setOnClickListener {
            mListener?.onItemClick(dataModel)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    class GridLayoutViewHolder(view: View): RecyclerView.ViewHolder(view){

        var texto: AppCompatTextView = view.findViewById(R.id.vh_gridlayout_texto)
        var image: AppCompatImageView = view.findViewById(R.id.vh_gridlayout_image)
        var container: RelativeLayout = view.findViewById(R.id.vh_gridlayout_relativeLayout)
        fun setData(item: DataModel){
            texto.text = item.text
            image.setImageResource(item.drawable)
            container.setBackgroundColor(Color.parseColor(item.color))
        }

    }

    interface ItemListener {
        fun onItemClick(item: DataModel)
    }

}
