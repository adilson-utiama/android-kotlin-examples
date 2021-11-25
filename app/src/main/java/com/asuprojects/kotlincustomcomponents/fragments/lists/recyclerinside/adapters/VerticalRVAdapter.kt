package com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.adapters

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.models.HorizontalRVModel
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import kotlinx.android.synthetic.main.viewholder_vertical_header.view.*
import kotlinx.android.synthetic.main.viewholder_vertical_item.view.*

class VerticalRVAdapter(
    private val category: String,
    private val subcategory: ArrayList<String>,
    private val colors: ArrayList<ArrayList<HorizontalRVModel>>
) : Section(
    SectionParameters.builder().itemResourceId(
        R.layout.viewholder_vertical_item
    ).headerResourceId(R.layout.viewholder_vertical_header).build()
) {

    // Size of Subcategories in each Section
    override fun getContentItemsTotal(): Int {
        return subcategory.size
    }

    class SectionViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)

    class ItemViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        private val horizontalRecyclerView: RecyclerView
        val horizontalAdapter: HorizontalRVAdapter

        init {
            val context = itemView.context
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_rv)
            horizontalRecyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            horizontalRecyclerView?.addItemDecoration(
                EqualSpacingItemDecoration(
                    8,
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            horizontalAdapter = HorizontalRVAdapter(context)
            horizontalRecyclerView.adapter = horizontalAdapter
        }
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return SectionViewHolder(view)
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }


    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        holder.itemView.vertical_category_text.setTextColor(Color.WHITE)
        holder.itemView.vertical_category_text.text = category
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemViewHolder
        // Subcategory
        itemHolder.itemView.subcategory_text.text = subcategory[position]
        // Color items
        itemHolder.horizontalAdapter.setColorsData(colors[position])
        // Pass the current row
        itemHolder.horizontalAdapter.setRowIndex(position)
    }
}