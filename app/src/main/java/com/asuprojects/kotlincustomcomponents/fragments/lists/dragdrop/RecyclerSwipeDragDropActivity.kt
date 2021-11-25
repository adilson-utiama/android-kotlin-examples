package com.asuprojects.kotlincustomcomponents.fragments.lists.dragdrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SwipeDragDropItemAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.utils.SwipeAndDragDropHelper
import com.asuprojects.kotlincustomcomponents.helpers.RandomValues
import kotlinx.android.synthetic.main.activity_recycler_swipe_actions.*
import kotlinx.android.synthetic.main.activity_recycler_swipe_drag_drop.*

class RecyclerSwipeDragDropActivity : AppCompatActivity(), SwipeAndDragDropHelper.StartDragListener {

    private var items = RandomValues.generateStrings(20)
    private lateinit var adapter: SwipeDragDropItemAdapter
    private lateinit var touchHelper: ItemTouchHelper

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
        setContentView(R.layout.activity_recycler_swipe_drag_drop)

        supportActionBar?.apply {
            this.title = "Swipe Drag Drop"
            this.setDisplayHomeAsUpEnabled(true)
        }

        adapter = SwipeDragDropItemAdapter(items, this)
        adapter.setOnClickItem(object: SwipeDragDropItemAdapter.OnClickItem{
            override fun onClickSWipeDragDropItem(view: View, position: Int) {
                val text = items[position]
                Toast.makeText(this@RecyclerSwipeDragDropActivity, "Text: $text", Toast.LENGTH_SHORT).show()
            }

        })
        val callback = SwipeAndDragDropHelper(this,
            items,
            adapter,
            R.drawable.ic_delete_white, R.drawable.ic_edit_white)

        recycler_swipe_drag_drop.adapter = adapter

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler_swipe_drag_drop)

        recycler_swipe_drag_drop.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_recycler_swipe_drag_drop.show()

                } else if (dy > 0) {
                    fab_recycler_swipe_drag_drop.hide()
                }
            }
        })

    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }
}
