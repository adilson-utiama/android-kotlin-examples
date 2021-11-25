package com.asuprojects.kotlincustomcomponents.prototypes.recyclrsnapzoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

data class SnapZoomItem(val bgColor: Int, val name: String)

class SnapZoomAdapter(val items: List<SnapZoomItem>)
    : RecyclerView.Adapter<SnapZoomAdapter.SnapZoomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapZoomViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_snap_zoom_item, parent, false)
        return SnapZoomViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SnapZoomViewHolder, position: Int) {
        val snapZoomItem = items[position]
        holder.name.text = snapZoomItem.name
        holder.card.setCardBackgroundColor(snapZoomItem.bgColor)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SnapZoomViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card: CardView = view.findViewById(R.id.vh_snap_zoom_cardview)
        val name: AppCompatTextView = view.findViewById(R.id.vh_snap_zoom_name)
    }
}