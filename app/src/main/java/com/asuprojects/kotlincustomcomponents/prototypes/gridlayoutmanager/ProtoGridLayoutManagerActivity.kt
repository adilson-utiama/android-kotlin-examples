package com.asuprojects.kotlincustomcomponents.prototypes.gridlayoutmanager

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_proto_grid_layout_manager.*

class ProtoGridLayoutManagerActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proto_grid_layout_manager)

        supportActionBar?.apply {
            title = "GridLayoutManager"
            setDisplayHomeAsUpEnabled(true)
        }

        val lista = mutableListOf<GridItem>(
            GridItem(Color.parseColor("#09A9FF"), R.drawable.g1_battle, "Item 1"),
            GridItem(Color.parseColor("#3E51B1"), R.drawable.g2_beer, "Item 2"),
            GridItem(Color.parseColor("#673BB7"), R.drawable.g3_ferrari, "Item 3"),
            GridItem(Color.parseColor("#4BAA50"), R.drawable.g4_jetpack_joyride, "Item 4"),
            GridItem(Color.parseColor("#F94336"), R.drawable.g5_terraria, "Item 5"),
            GridItem(Color.parseColor("#0A9B88"), R.drawable.g6_three_d, "Item 6"),
            GridItem(Color.parseColor("#09A9FF"), R.drawable.g1_battle, "Item 7")
        )

        val adapter = ProtoGridLayoutAdapter(lista)

        recycler_proto_grid_layout_manager.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        recycler_proto_grid_layout_manager.adapter = adapter
    }
}