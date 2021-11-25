package com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_persistent_bottom_sheet.*

class PersistentBottomSheetActivity : AppCompatActivity() {

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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_persistent_bottom_sheet)

        supportActionBar?.apply {
            this.title = "Persistent Bottom Sheet"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val sheetBehavior = BottomSheetBehavior.from(my_bottom_sheet)

        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        btnPersistentBottomSheet.text = "Close Bottom Sheet"
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        btnPersistentBottomSheet.text = "Open Bottom Sheet"
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                }
            }

        })

        btnPersistentBottomSheet.setOnClickListener {
            if(sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                btnPersistentBottomSheet.text = "Close Bottom Sheet"
            }else{
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                btnPersistentBottomSheet.text = "Open Bottom Sheet"
            }
        }


        btn_pay.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            btnPersistentBottomSheet.text = "Open Bottom Sheet"
            ToastUtil.msg(this@PersistentBottomSheetActivity, "Payment Done!!")
        }
    }
}
