package com.asuprojects.kotlincustomcomponents.prototypes.collapsingtoolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.SimpleItemTextAdapter
import kotlinx.android.synthetic.main.activity_collapsing_tool_bar.*

class CollapsingToolBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_tool_bar)

        setSupportActionBar(screen_collapsing_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val adapter = SimpleItemTextAdapter(mutableListOf<String>(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7",
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"
        ))
        recycler_view_collapsing_toolbar.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.popup_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.popup_menu_edit -> {

            }
            R.id.popup_menu_delete -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}