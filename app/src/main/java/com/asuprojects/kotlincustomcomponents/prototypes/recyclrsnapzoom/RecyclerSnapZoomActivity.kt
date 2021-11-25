package com.asuprojects.kotlincustomcomponents.prototypes.recyclrsnapzoom

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_recycler_snap_zoom.*

class RecyclerSnapZoomActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_snap_zoom)

        supportActionBar?.apply {
            title = "Snap Zoom"
            setDisplayHomeAsUpEnabled(true)
        }

        val items = listOf(
            SnapZoomItem(Color.BLUE, "Item 1"),
            SnapZoomItem(Color.RED, "Item 2"),
            SnapZoomItem(Color.GREEN, "Item 3"),
            SnapZoomItem(Color.LTGRAY, "Item 4"),
            SnapZoomItem(Color.BLACK, "Item 5"),
            SnapZoomItem(Color.MAGENTA, "Item 6"),
            SnapZoomItem(Color.DKGRAY, "Item 7"),
            SnapZoomItem(Color.CYAN, "Item 8")
        )

        recycler_view_snap_zoom_horizontal.layoutManager = CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_snap_zoom_horizontal.adapter = SnapZoomAdapter(items)
        PagerSnapHelper().attachToRecyclerView(recycler_view_snap_zoom_horizontal)
        //LinearSnapHelper().attachToRecyclerView(recycler_view_snap_zoom_horizontal)

        recycler_view_snap_zoom_vertical.layoutManager = CenterZoomLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view_snap_zoom_vertical.adapter = SnapZoomAdapter(items)
        PagerSnapHelper().attachToRecyclerView(recycler_view_snap_zoom_vertical)
    }
}