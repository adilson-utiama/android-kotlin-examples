package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderProcedureHorizontalBinding

class ProceduresHAdapter : RecyclerView.Adapter<ProceduresHAdapter.ProcedureVH>() {

    class ProcedureVH(binding: ViewholderProcedureHorizontalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureVH {
        return ProcedureVH(
            ViewholderProcedureHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProcedureVH, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }
}