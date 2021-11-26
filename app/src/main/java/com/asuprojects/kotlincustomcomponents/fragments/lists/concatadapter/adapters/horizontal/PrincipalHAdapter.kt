package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderPrincipalHorizontalBinding

class PrincipalHAdapter : RecyclerView.Adapter<PrincipalHAdapter.PrincipalVH>() {

    private var listener: OnClickListener? = null

    fun setOnClickListener(l: OnClickListener){
        this.listener = l
    }

    class PrincipalVH(private val binding: ViewholderPrincipalHorizontalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){

        }

        fun getBind(): ViewholderPrincipalHorizontalBinding = binding
    }

    interface OnClickListener {
        fun onClickDetails(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrincipalVH {
        return PrincipalVH(
            ViewholderPrincipalHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PrincipalVH, position: Int) {

        holder.getBind().vhPrincipalHViewDetails.setOnClickListener {
            listener?.onClickDetails("Adapter Principal Horizontal - absolute_adapter_position: ${holder.absoluteAdapterPosition}, binding_adapter_position: ${holder.bindingAdapterPosition}")
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}