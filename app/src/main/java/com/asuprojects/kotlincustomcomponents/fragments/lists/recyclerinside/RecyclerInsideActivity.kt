package com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside

import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.adapters.VerticalRVAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_recycler_inside.*
import java.lang.ref.WeakReference

class RecyclerInsideActivity : AppCompatActivity() {

    private val sectionAdapter = SectionedRecyclerViewAdapter()

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
        setContentView(R.layout.activity_recycler_inside)

        supportActionBar?.apply {
            this.title = "Recyclerview Inside"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vertical_recycler.layoutManager = layoutManager
        vertical_recycler.adapter = sectionAdapter

        FetchDemoData(this, vertical_recycler, sectionAdapter).execute()
    }

    private class FetchDemoData
    internal constructor(
        mContext: RecyclerInsideActivity,
        verticalRecyclerView: RecyclerView,
        sectionAdapter: SectionedRecyclerViewAdapter
    ) :
        AsyncTask<Void, Void, Void>() {

        private val activityReference: WeakReference<RecyclerInsideActivity> =
            WeakReference(mContext)

        var verticalRv = verticalRecyclerView
        var secAdapter = sectionAdapter

        val colors = Colors().objectsArray

        override fun doInBackground(vararg params: Void): Void? {
            for (color in colors) {
                secAdapter.addSection(
                    VerticalRVAdapter(
                        color.category,
                        color.subcategory,
                        color.colors
                    )
                )
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            // get a reference to the activity if it is still there
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
            verticalRv.adapter?.notifyDataSetChanged()
        }
    }
}