package com.asuprojects.kotlincustomcomponents.fragments.lists.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import java.util.*
import kotlin.collections.ArrayList

class SimpleItemFilterAdapter(val paises: MutableList<String>)
    : RecyclerView.Adapter<SimpleItemFilterAdapter.SimpleFilterViewHolder>(), Filterable{

    var paisesFilterList = mutableListOf<String>()

    init {
        paisesFilterList = paises
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleFilterViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_simple_item_filter, parent, false)
        return SimpleFilterViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SimpleFilterViewHolder, position: Int) {
        val pais = paisesFilterList[position]
        holder.pais.text = pais
    }

    override fun getItemCount(): Int {
        return paisesFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                Log.i("TESTE", "performFiltering: $charSearch")
                if (charSearch.isEmpty()) {
                    paisesFilterList = paises
                } else {
                    val resultList = mutableListOf<String>()
                    for (row in paises) {
                        if (row.toLowerCase(Locale.getDefault()).contains(charSearch.toLowerCase(Locale.getDefault()))) {
                            resultList.add(row)
                        }
                    }
                    paisesFilterList = resultList

                    Log.i("TESTE", "performFiltering: $paisesFilterList")
                }
                val filterResults = FilterResults()
                filterResults.values = paisesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                paisesFilterList = results?.values as MutableList<String>

                Log.i("TESTE", "publishResults: $paisesFilterList")
                notifyDataSetChanged()
            }

        }
    }

    class SimpleFilterViewHolder(view: View): RecyclerView.ViewHolder(view){
        val pais: AppCompatTextView = view.findViewById(R.id.vh_text_simple_item_filter)
    }
}