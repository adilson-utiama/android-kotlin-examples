package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.databinding.ViewholderProcedureVerticalBinding

class ProceduresVAdapter : RecyclerView.Adapter<ProceduresVAdapter.ProcedureVH>() {

    class ProcedureVH(binding: ViewholderProcedureVerticalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureVH {
        return ProcedureVH(
            ViewholderProcedureVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProcedureVH, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }
}