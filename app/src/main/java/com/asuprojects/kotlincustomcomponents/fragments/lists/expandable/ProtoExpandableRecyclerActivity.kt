package com.asuprojects.kotlincustomcomponents.fragments.lists.expandable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_proto_expandable_recycler.*

class ProtoExpandableRecyclerActivity : AppCompatActivity() {

    private var interview = mutableListOf<InterviewModel>()
    private lateinit var adapter: ExpandableItemAdapter

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
        setContentView(R.layout.activity_proto_expandable_recycler)

        supportActionBar?.apply {
            this.title = "Expandable Recyclerview"
            this.setDisplayHomeAsUpEnabled(true)
        }

        interview = getInterview()

        adapter = ExpandableItemAdapter(interview)

        recycler_expandable_layout_item.adapter = adapter
        recycler_expandable_layout_item.setHasFixedSize(true)

    }

    private fun getInterview(): MutableList<InterviewModel> {
        val lista = mutableListOf<InterviewModel>()
        for(item in 1..10){
            lista.add(
                InterviewModel(
                    "Question $item",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                            "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                            "It has survived not only five centuries, but also the leap into electronic typesetting, " +
                            "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset " +
                            "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like " +
                            "Aldus PageMaker including versions of Lorem Ipsum."
                )
            )
        }
        return lista
    }
}