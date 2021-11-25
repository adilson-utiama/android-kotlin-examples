package com.asuprojects.kotlincustomcomponents.prototypes.expandableitem

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_expandable_recycler_item.*

class ExpandableRecyclerItemActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_recycler_item)

        supportActionBar?.apply {
            this.title = "Expandable Item"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val mainLlist = mutableListOf(
            ItemWithList(
                "Main Item 1", mutableListOf(
                    CheckableItem("Child Item 1", false, true),
                    CheckableItem("Child Item 2", false, true),
                    CheckableItem("Child Item 3", false, true),
                    CheckableItem("Child Item 4", false, true),
                    CheckableItem("Child Item 5", false, true),
                    CheckableItem("Child Item 6", false, true)
                ), false, false
            ),
            ItemWithList(
                "Main Item 2", mutableListOf(
                    CheckableItem("Child Item 1", false, true),
                    CheckableItem("Child Item 2", false, true),
                    CheckableItem("Child Item 3", false, true),
                    CheckableItem("Child Item 4", false, true)
                ), false, false
            ),
            ItemWithList(
                "Main Item 3", mutableListOf(
                    //CheckableItem("Lista Vazia", false, false)
                ), true, false
            )
        )


        val adapter = ExpandableItemAdapter(mainLlist, object : ExpandableItemAdapter.AddOnItemActionsChildListener {
            override fun onAddItemOnChild(view: View, checkableItem: CheckableItem) {
                ToastUtil.msg(this@ExpandableRecyclerItemActivity, "${checkableItem.name} Adicionado!")
            }

            override fun onClickChildItem(view: View,checkableItem: CheckableItem) {
                ToastUtil.msg(this@ExpandableRecyclerItemActivity, "Click on Child Item: ${checkableItem.name}")
            }

            override fun onCheckChildItem(view: View, checkableItem: CheckableItem) {
                ToastUtil.msg(this@ExpandableRecyclerItemActivity, "Checked Child Item: ${checkableItem.name}")
            }

            override fun onRemoveChildItem(view: View, checkableItem: CheckableItem) {
                ToastUtil.msg(this@ExpandableRecyclerItemActivity, "Checked Child Item Removed: ${checkableItem.name}")
            }

            override fun onUpdateChildItem(view: View, checkableItem: CheckableItem) {
                ToastUtil.msg(this@ExpandableRecyclerItemActivity, "Checked Child Item Updated: ${checkableItem.name}")
            }
        })
        recycler_custom_expandable_item.layoutManager = LinearLayoutManager(this)
        recycler_custom_expandable_item.adapter = adapter
    }
}