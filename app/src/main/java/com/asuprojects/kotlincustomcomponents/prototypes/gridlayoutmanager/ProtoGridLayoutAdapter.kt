package com.asuprojects.kotlincustomcomponents.prototypes.gridlayoutmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.viewholder_proto_grid_layout_item.view.*

class ProtoGridLayoutAdapter(val list: MutableList<GridItem>)
    : RecyclerView.Adapter<ProtoGridLayoutAdapter.ProtoGridItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProtoGridItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_proto_grid_layout_item, parent, false)
        return ProtoGridItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ProtoGridItemViewHolder, position: Int) {
        val item = list[position]
        holder.cardView.setCardBackgroundColor(item.color)
        holder.imageIcon.setImageResource(item.iconResource)
        holder.texto.text = item.title
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ProtoGridItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardView: CardView = view.findViewById(R.id.card_view_item)
        val imageIcon: AppCompatImageView = view.findViewById(R.id.vh_proto_gridlayout_image)
        val texto: AppCompatTextView = view.findViewById(R.id.vh_proto_gridlayout_texto)
    }
}