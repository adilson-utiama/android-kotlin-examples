package com.asuprojects.kotlincustomcomponents.fragments.lists.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SimpleItemMenuAdapter
import com.asuprojects.kotlincustomcomponents.helpers.RandomValues
import kotlinx.android.synthetic.main.activity_recycler_item_menu.*
import kotlinx.android.synthetic.main.activity_recycler_swipe_drag_drop.*

class RecyclerItemMenuActivity : AppCompatActivity() {

    private var items = RandomValues.generateStrings(20)
    private lateinit var adapter: SimpleItemMenuAdapter

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
        setContentView(R.layout.activity_recycler_item_menu)

        supportActionBar?.apply {
            this.title = "Item Popup Menu"
            this.setDisplayHomeAsUpEnabled(true)
        }

        adapter = SimpleItemMenuAdapter(items)
        adapter.setOnClickItemMenuListener(object: SimpleItemMenuAdapter.OnClickItemMenuListener{
            override fun onClickItem(view: View, position: Int) {
                val texto = items[position]
                Toast.makeText(this@RecyclerItemMenuActivity, "Item: $texto", Toast.LENGTH_SHORT).show()
            }

            override fun onClickMenu(view: View, position: Int) {
                val texto = items[position]
                val popupMenu = PopupMenu(this@RecyclerItemMenuActivity, view)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {menuItem ->
                    when(menuItem.itemId){
                        R.id.popup_menu_edit -> {
                            Toast.makeText(this@RecyclerItemMenuActivity, "Item: $texto Editado!!", Toast.LENGTH_SHORT).show()
                        }
                        R.id.popup_menu_delete -> {
                            Toast.makeText(this@RecyclerItemMenuActivity, "Item: $texto Deletado!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    true
                }
                popupMenu.show()
            }

        })

        recycler_item_menu.adapter = adapter
        recycler_item_menu.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_recycler_item_menu.show()

                } else if (dy > 0) {
                    fab_recycler_item_menu.hide()
                }
            }
        })
    }
}
