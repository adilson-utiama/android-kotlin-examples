package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class SimpleTextAdapter(val textos: MutableList<String>)
    : RecyclerView.Adapter<SimpleTextAdapter.SimpleTextViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_simple_text, parent, false)
        return SimpleTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        val texto = textos[position]
        holder.textContent.text = texto
    }

    override fun getItemCount(): Int {
        return textos.size
    }


    class SimpleTextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textContent = view.findViewById<AppCompatTextView>(R.id.vh_simple_text)
    }
}
