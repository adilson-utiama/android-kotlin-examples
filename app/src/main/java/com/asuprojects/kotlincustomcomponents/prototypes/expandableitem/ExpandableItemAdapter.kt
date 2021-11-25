package com.asuprojects.kotlincustomcomponents.prototypes.expandableitem

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandable.expandablelayout.ExpandableLayout
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class ExpandableItemAdapter(
    val items: MutableList<ItemWithList>,
    val addOnItemActionsChildListener: AddOnItemActionsChildListener
) : RecyclerView.Adapter<ExpandableItemAdapter.ExpandableItemViewHolder>() {

    // Save the expanded row position
    private val expandedPositionSet: HashSet<Int> = HashSet()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_expandable_recycler, parent, false)
        context = parent.context
        return ExpandableItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ExpandableItemViewHolder, position: Int) {
        val itemWithList = items[position]
        holder.expandableLayout.isClickable = false
        holder.title.text = itemWithList.title
        if (!itemWithList.withCheckbox) {
            holder.checkbox.visibility = GONE
        } else {
            holder.iconAdd.visibility = VISIBLE
        }
        holder.expandableLayout.setOnExpandListener(object : ExpandableLayout.OnExpandListener {
            override fun onExpand(expanded: Boolean) {
                if (expandedPositionSet.contains(position)) {
                    holder.iconArrow.animate().rotation(0F).duration = 300
                    expandedPositionSet.remove(position)
                } else {
                    holder.iconArrow.animate().rotation(180F).duration = 300
                    expandedPositionSet.add(position)
                }
            }
        })
        holder.iconArrow.setOnClickListener {
            holder.expandableLayout.toggleExpandView()
        }

        // Recycler List do Item
        val adapterItems = CheckableItemAdapter(itemWithList.list)

        adapterItems.setOnCheckItemListener(object : CheckableItemAdapter.OnCheckItemListener {
            override fun onClickItem(view: View, position: Int) {
                val checkableItem = itemWithList.list[position]

                Log.i("TESTE", "ITEM_ADAPTER Checkable position: $position")

                // Action Add Here
                val popupMenu = PopupMenu(context, view)
                popupMenu.menuInflater.inflate(R.menu.popupmenu_checkable_item, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.pm_checkable_item_editart -> {
                            ToastUtil.msg(context, "Clicked on Editar")
                            checkableItem.name = "Updated Item"
                            adapterItems.notifyItemChanged(position, checkableItem)
                            addOnItemActionsChildListener.onUpdateChildItem(view, checkableItem)
                        }
                        R.id.pm_checkable_item_deletar -> {
                            addOnItemActionsChildListener.onRemoveChildItem(view, checkableItem)

                            adapterItems.removeItem(position)

                            val viewHolder = adapterItems.views[holder.adapterPosition]
                            viewHolder.measure(
                                View.MeasureSpec.makeMeasureSpec(holder.recycler.width, View.MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                            val adapterViewHeight = view.measuredHeight

                            holder.expandableLayout.decreaseLayoutSize(adapterViewHeight)
                            //itemWithList.list.removeAt(position)
                        }
                        R.id.pm_checkable_item_info -> {
                            ToastUtil.msg(context, "Clicked on Info")
                            //addOnItemActionsChildListener.onUpdateChildItem(view, checkableItem)
                        }
                    }
                    true
                }
                popupMenu.show()

                addOnItemActionsChildListener.onClickChildItem(view, checkableItem)
            }
            override fun onCheckItem(view: View, position: Int, checked: Boolean) {
                val checkableItem = itemWithList.list[position]
                addOnItemActionsChildListener.onCheckChildItem(view, checkableItem)
            }
        })

        //holder.recycler.setHasFixedSize(true)
        holder.recycler.adapter = adapterItems

        holder.iconAdd.setOnClickListener {
            if(!holder.expandableLayout.isExpanded){
                holder.expandableLayout.expandExpandView()
            }
            val checkableItem = CheckableItem("Item Added", false, true)
            adapterItems.addItem(checkableItem)

            Log.i("TESTE", "ITEM_ADAPTER BEFORE Parent ViewGroup size: ${adapterItems.views.size}")

            // Um Delay para esperar o Adapter construir o ViewHolder
            // Divida Tecnica AQUI
            GlobalScope.launch(Dispatchers.Main) {
                delay(400)

                Log.i("TESTE", "ITEM_ADAPTER LATER Parent ViewGroup size: ${adapterItems.views.size}")
                Log.i("TESTE", "ITEM_ADAPTER Holder Adapter Position: ${holder.adapterPosition}")

                try{
                    val view = adapterItems.views[holder.adapterPosition]
                    // Medir Manualmente a altura da View
                    view.measure(
                        View.MeasureSpec.makeMeasureSpec(holder.recycler.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    val adapterViewHeight = view.measuredHeight

                    Log.i("TESTE", "ITEM_ADAPTER Checkable Item View Height: $adapterViewHeight")

                    holder.expandableLayout.increaseLayoutSize(adapterViewHeight)
                }catch(e: Exception){
                    Log.e("TESTE", "Erro ao expandir Layout: ${e.message}")

                    // Tentar calcular a partir da altura total
                    holder.recycler.measure(
                        View.MeasureSpec.makeMeasureSpec(holder.recycler.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    val height = holder.recycler.measuredHeight
                    Log.i("TESTE", "ITEM_ADAPTER RecyclerView Height: $height")
                    holder.expandableLayout.increaseLayoutSize(height)
                }
                addOnItemActionsChildListener.onAddItemOnChild(it, checkableItem)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface AddOnItemActionsChildListener {
        fun onAddItemOnChild(view: View, checkableItem: CheckableItem)
        fun onClickChildItem(view: View, checkableItem: CheckableItem)
        fun onCheckChildItem(view: View, checkableItem: CheckableItem)
        fun onRemoveChildItem(view: View, checkableItem: CheckableItem)
        fun onUpdateChildItem(view: View, checkableItem: CheckableItem)
    }

    class ExpandableItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expandableLayout: ExpandableLayout =
            view.findViewById(R.id.vh_expandable_recycler_container)
        val title: AppCompatTextView = view.findViewById(R.id.vh_expandable_recycler_title)
        val checkbox: AppCompatCheckBox = view.findViewById(R.id.vh_expandable_recycler_checkbox)
        val iconAdd: AppCompatImageView = view.findViewById(R.id.vh_expandable_recycler_add)
        val iconArrow: AppCompatImageView = view.findViewById(R.id.vh_expandable_recycler_arrow)
        val recycler: RecyclerView = view.findViewById(R.id.vh_recycler_items)
    }

}