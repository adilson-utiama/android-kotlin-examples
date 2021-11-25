package com.asuprojects.kotlincustomcomponents.fragments.lists.gridlayoutmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_grid_layout_manager.*

class GridLayoutManagerActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_layout_manager)

        supportActionBar?.apply {
            this.title = "GridLayoutManager"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val values = mutableListOf(
            DataModel("Item 1", R.drawable.g1_battle, "#09A9FF"),
            DataModel("Item 2", R.drawable.g2_beer, "#3E51B1"),
            DataModel("Item 3", R.drawable.g3_ferrari, "#673BB7"),
            DataModel("Item 4", R.drawable.g4_jetpack_joyride, "#4BAA50"),
            DataModel("Item 5", R.drawable.g6_three_d, "#F94336"),
            DataModel("Item 6", R.drawable.g5_terraria, "#0A9B88")
        )

        recycler_gridlayout_manager.layoutManager = AutoFitGridLayoutManager(this, 250)
        //recycler_gridlayout_manager.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        recycler_gridlayout_manager.adapter = RecyclerGridLayoutAdaper(values, object: RecyclerGridLayoutAdaper.ItemListener{
            override fun onItemClick(item: DataModel) {
                ToastUtil.msg(this@GridLayoutManagerActivity, "Selected: ${item.text}")
            }
        })
    }
}