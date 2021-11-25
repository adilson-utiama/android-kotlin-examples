package com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.CardScaleHelper
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.utils.BlurBitmapUtils
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.utils.ViewSwitchUtils
import kotlinx.android.synthetic.main.activity_recycler_card_scale.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecyclerCardScaleActivity : AppCompatActivity() {

    private var mList = mutableListOf<Int>()
    private lateinit var mCardScaleHelper: CardScaleHelper
    private lateinit var mBlurRunnable: Runnable
    private var mLastPos = -1

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val decorView: View = window.decorView
//            val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//            decorView.systemUiVisibility = option
//            window.statusBarColor = Color.TRANSPARENT
//        }
        setContentView(R.layout.activity_recycler_card_scale)

        supportActionBar?.apply {
            title = "Card Scale"
            setDisplayHomeAsUpEnabled(true)
        }

        init()

    }

    private fun init() {
        for(a in 0..10){
            mList.add(R.drawable.pic4)
            mList.add(R.drawable.pic5)
            mList.add(R.drawable.pic6)
        }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_card_scale.layoutManager = linearLayoutManager
        recycler_view_card_scale.adapter = CardAdapterKotlin(mList.toList())

        mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper.currentItemPos = 2
        mCardScaleHelper.attachToRecyclerView(recycler_view_card_scale)

        initBlurBackground()
    }

    private fun initBlurBackground() {
        card_scale_blur_view
        recycler_view_card_scale.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    notifyBackgroundChange()
                }
            }
        })
        notifyBackgroundChange()
    }

    private fun notifyBackgroundChange() {
        if(mLastPos == mCardScaleHelper.currentItemPos) return
        mLastPos = mCardScaleHelper.currentItemPos
        val resId = mList[mCardScaleHelper.currentItemPos]

        GlobalScope.launch(Dispatchers.Main) {
            //delay(1000)
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            ViewSwitchUtils.startSwitchBackgroundAnim(
                baseContext,
                card_scale_blur_view,
                BlurBitmapUtils.getBlurBitmap(card_scale_blur_view.context, bitmap, 15)
            )
        }

//        mBlurRunnable = Runnable {
//            val bitmap = BitmapFactory.decodeResource(resources, resId)
//            ViewSwitchUtils.startSwitchBackgroundAnim(
//                baseContext,
//                card_scale_blur_view,
//                BlurBitmapUtils.getBlurBitmap(card_scale_blur_view.context, bitmap, 15)
//            )
//        }
//        card_scale_blur_view.postDelayed(mBlurRunnable, 500)
//        card_scale_blur_view.removeCallbacks(mBlurRunnable)
    }
}