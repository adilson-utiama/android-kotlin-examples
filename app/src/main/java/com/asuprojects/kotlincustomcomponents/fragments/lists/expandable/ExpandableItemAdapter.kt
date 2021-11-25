package com.asuprojects.kotlincustomcomponents.fragments.lists.expandable

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandable.expandablelayout.ExpandableLayout
import kotlinx.android.synthetic.main.viewholder_expandable_item.view.*

class ExpandableItemAdapter(val expandableItems: MutableList<InterviewModel>)
    : RecyclerView.Adapter<ExpandableItemAdapter.ExpandableViewHolder>() {

    // Save the expanded row position
    private val expandedPositionSet: HashSet<Int> = HashSet()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_expandable_item, parent, false)
        context = parent.context
        return ExpandableViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {

        holder.itemView.expandable_top_layout_title.text = expandableItems[position].question
        holder.itemView.expandable_layout_content.text = expandableItems[position].answer

        holder.itemView.expandable_item_layout.setOnExpandListener(object : ExpandableLayout.OnExpandListener{
            override fun onExpand(expanded: Boolean) {
                if (expandedPositionSet.contains(position)) {
                    holder.itemView.expandable_layout_icon.animate().rotation(0F).duration = 300
                    expandedPositionSet.remove(position)
                } else {
                    holder.itemView.expandable_layout_icon.animate().rotation(180F).duration = 300
                    expandedPositionSet.add(position)
                }
            }

        })
        //Desativa clique no item
        holder.itemView.expandable_item_layout.isClickable = false
        holder.itemView.expandable_item_layout.setExpand(expandedPositionSet.contains(position))

        holder.itemView.expandable_layout_icon.setOnClickListener {
            holder.itemView.expandable_item_layout.toggleExpandView()
        }
        holder.itemView.expandable_layout_icon_add.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return expandableItems.size
    }

    class ExpandableViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}
}