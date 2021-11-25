package com.asuprojects.kotlincustomcomponents.fragments.lists.swipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.OnClickItem
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SimpleSwipeItemAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.utils.SwipeHelper
import com.asuprojects.kotlincustomcomponents.helpers.RandomValues
import kotlinx.android.synthetic.main.activity_recycler_swipe_actions.*

class RecyclerSwipeActionsActivity : AppCompatActivity() {

    private var items = RandomValues.generateStrings(20)
    private lateinit var adapter: SimpleSwipeItemAdapter

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
        setContentView(R.layout.activity_recycler_swipe_actions)

        supportActionBar?.apply {
            this.title = "Swipe Action"
            this.setDisplayHomeAsUpEnabled(true)
        }

        adapter = SimpleSwipeItemAdapter(items)
        adapter.setOnClickItem(object: OnClickItem{
            override fun onClickSimpleItem(view: View, position: Int) {
                val text = items[position]
                Toast.makeText(this@RecyclerSwipeActionsActivity, "Text: $text", Toast.LENGTH_SHORT).show()
            }

        })
        recycler_swipe_example.adapter = adapter

        recycler_swipe_example.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_recycler_swipe.show()

                } else if (dy > 0) {
                    fab_recycler_swipe.hide()
                }
            }
        })

        SwipeHelper(this, items, adapter).enableSwipe(recycler_swipe_example)

    }
}
