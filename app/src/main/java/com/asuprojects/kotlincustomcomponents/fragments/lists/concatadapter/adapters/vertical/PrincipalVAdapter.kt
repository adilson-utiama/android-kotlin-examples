package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderPrincipalVerticalBinding

class PrincipalVAdapter : RecyclerView.Adapter<PrincipalVAdapter.PrincipalVH>() {

    private var listener: OnClickListener? = null

    fun setListener(l: OnClickListener){
        this.listener = l
    }

    class PrincipalVH(private val binding: ViewholderPrincipalVerticalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){

        }
        fun getBind(): ViewholderPrincipalVerticalBinding = binding
    }

    interface OnClickListener {
        fun onClickDetails(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrincipalVH {
        return PrincipalVH(
            ViewholderPrincipalVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PrincipalVH, position: Int) {

        holder.getBind().vhPrincipalVViewDetails.setOnClickListener {
            listener?.onClickDetails("Adapter Principal Vertical - absolute_adapter_position: ${holder.absoluteAdapterPosition}, binding_adapter_position: ${holder.bindingAdapterPosition}")
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}