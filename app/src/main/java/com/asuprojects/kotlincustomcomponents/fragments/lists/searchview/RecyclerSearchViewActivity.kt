package com.asuprojects.kotlincustomcomponents.fragments.lists.searchview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SimpleItemFilterAdapter
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.SimpleItemTextAdapter
import kotlinx.android.synthetic.main.activity_recycler_search_view.*

class RecyclerSearchViewActivity : AppCompatActivity() {

    private var paises = mutableListOf<String>()
    private lateinit var adapter: SimpleItemFilterAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_search_view)

        supportActionBar?.apply {
            this.title = "SearchView"
            this.setDisplayHomeAsUpEnabled(true)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("TESTE", "onQueryTextChange: $newText")
                adapter.filter.filter(newText)
                return false
            }

        })
        mountListOfCountries()
    }

    private fun mountListOfCountries() {
        paises = getData()

        // O Adapter do Recycler precisa implementar Filterable
        adapter = SimpleItemFilterAdapter(paises)
        recycler_searchview.adapter = adapter
    }

    private fun getData(): MutableList<String> {
        return mutableListOf(
            "Brasil", "Japão", "EUA", "Italia", "Coreia do Sul", "Taiwan", "Alemanha", "Servia", "Croacia", "Russia",
            "Espanha", "Mexico", "Portugual", "França", "Reino Unido", "Suecia", "Noruega", "Eslovaquia", "Eslovenia",
            "Africa do Sul", "Madagascar", "Egito", "Israel", "Emirados Arabes", "India", "Uruguay", "Chile", "Canada",
            "Tailandia", "Australia", "Nova Zelandia", "Suiça", "Indonesia"
        )
    }
}