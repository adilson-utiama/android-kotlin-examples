package com.asuprojects.kotlincustomcomponents.prototypes

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SimpleSwipeItemAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.utils.SwipeHelper
import com.asuprojects.kotlincustomcomponents.helpers.RandomValues
import com.asuprojects.kotlincustomcomponents.helpers.SoftKeyboardUtil
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_add_item_list.*
import kotlinx.android.synthetic.main.layout_add_item_bottomsheet.*

class AddItemListActivity : AppCompatActivity() {

    private var list = mutableListOf<String>()
    private lateinit var adapter: SimpleSwipeItemAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.options_menu_add_item_done -> {
                ToastUtil.msg(baseContext, "Button Done Clicked!!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_add_item_list)

        supportActionBar?.apply {
            this.title = "Add Item Screen"
            this.setDisplayHomeAsUpEnabled(true)
        }

        list = RandomValues.generateStrings(5)
        adapter = SimpleSwipeItemAdapter(list)
        recycler_proto_add_item_list.layoutManager = LinearLayoutManager(this)
        recycler_proto_add_item_list.adapter = adapter
        recycler_proto_add_item_list.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_open_close_dialog.show()
                    Log.i("TESTE", "Show FAB Button")
                } else if (dy > 0) {
                    fab_open_close_dialog.hide()
                    Log.i("TESTE", "Hide FAB Button")
                }
            }
        })
        //Enable Swipe
        SwipeHelper(this, list, adapter).enableSwipe(recycler_proto_add_item_list)

        val sheetBehavior = BottomSheetBehavior.from(add_item_top_view)
        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        fab_open_close_dialog.animate().rotation(45F).duration = 300
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fab_open_close_dialog.animate().rotation(0F).duration = 300
                        SoftKeyboardUtil.hideSoftKeyboard(this@AddItemListActivity, layout_add_item_input)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                }
            }

        })

        fab_open_close_dialog.setOnClickListener {
            if(sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                fab_open_close_dialog.animate().rotation(45F).duration = 300
            }else{
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                fab_open_close_dialog.animate().rotation(0F).duration = 300
            }
        }

        layout_add_item_input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if(value.toString().isNotEmpty()){
                    input_layout_add_item.isErrorEnabled = false
                }
            }

        })

        btn_proto_add_item.setOnClickListener {
            if(layout_add_item_input.text.toString().isEmpty()){
                input_layout_add_item.error = "Informe uma descrição para o item"
                input_layout_add_item.isErrorEnabled = true
            }else{
                val value = layout_add_item_input.text.toString()
                list.add(value)
                adapter.notifyDataSetChanged()

                layout_add_item_input.text!!.clear()

                recycler_proto_add_item_list.smoothScrollToPosition(recycler_proto_add_item_list.bottom)

                ToastUtil.msg(baseContext, "Um Item foi Adicionado!!")
            }
        }
    }

}
