package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderNecessaryItemBinding
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderNecessaryItemsVerticalBinding

class NecessaryItemsVAdapter : RecyclerView.Adapter<NecessaryItemsVAdapter.NecessaryItemsVH>() {

    private var listener: OnClickListener? = null

    fun setListener(l: OnClickListener){
        this.listener = l
    }

    class NecessaryItemsVH(private val binding: ViewholderNecessaryItemsVerticalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){}

        fun getBinding(): ViewholderNecessaryItemsVerticalBinding = binding
    }

    interface OnClickListener{
        fun onClickDetails(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NecessaryItemsVH {
        return NecessaryItemsVH(
            ViewholderNecessaryItemsVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NecessaryItemsVH, position: Int) {

        val items = listOf(
            "4 xícaras de trigo", "4 xícaras de leite", "1 caldo de galinha", "1/2 colher de margarina"
        )
        val itemAdapter = ItemsAdapter(items)

        holder.getBinding().recyclerVerticalItems.adapter = itemAdapter

        holder.getBinding().vhNiVViewDetails.setOnClickListener {
            listener?.onClickDetails("Adapter Necessary Items Vertical - absolute_adapter_position: ${holder.absoluteAdapterPosition}, binding_adapter_position: ${holder.bindingAdapterPosition}")
        }

    }

    override fun getItemCount(): Int {
        return 1
    }

    class ItemsAdapter(private val items: List<String>)
        : RecyclerView.Adapter<ItemsAdapter.ItemVH>(){

        class ItemVH(private val binding: ViewholderNecessaryItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(text: String) {
                binding.vhItemDescription.text = text
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
            return ItemVH(
                ViewholderNecessaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: ItemVH, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

}