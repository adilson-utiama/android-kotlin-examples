package com.asuprojects.kotlincustomcomponents.prototypes.blurimage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin.CardAdapterKotlin
import com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin.CardScaleHelperKotlin
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.utils.BlurBitmapUtils
import com.asuprojects.kotlincustomcomponents.prototypes.recyclercardscale.javacode.utils.ViewSwitchUtils
import kotlinx.android.synthetic.main.activity_blur_background.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlurBackgroundActivity : AppCompatActivity() {

    private lateinit var adapter: CardAdapterKotlin

    private var mList = mutableListOf<Int>()
    private lateinit var mCardScaleHelper: CardScaleHelperKotlin
    private var mLastPos = -1

    private var currentBlurRadius: Int = 1
    private var currentImageResIdSelected: Int = 0

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_background)

        supportActionBar?.apply {
            title = "Blur Image"
            setDisplayHomeAsUpEnabled(true)
        }

        recycler_view_images_collection
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            blur_screen_seekbar.min = 1
            blur_screen_seekbar.max = 24
        }
        //blur_screen_seekbar.incrementProgressBy(5)
        blur_screen_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.apply {
                    currentBlurRadius = progress + 1
                    blurBackground(currentImageResIdSelected, currentBlurRadius)

                    GlobalScope.launch(Dispatchers.Main) {
                        val value = "Blur: ${currentBlurRadius - 1}"
                        blur_screen_radius.text = value
                    }
                }
            }

        })
        blur_screen_radius
        blur_screen_pick_image

        init()
    }

    private fun init() {
        mList.add(R.drawable.pic4)
        mList.add(R.drawable.paisagem_praia)
        mList.add(R.drawable.paisagem001)
        mList.add(R.drawable.paisagem002)
        mList.add(R.drawable.paisagem003)
        mList.add(R.drawable.paisagem004)
        mList.add(R.drawable.paisagem005)
        mList.add(R.drawable.paisagem006)

        currentImageResIdSelected = R.drawable.pic4

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_images_collection.layoutManager = linearLayoutManager
        adapter = CardAdapterKotlin(mList.toList())
        adapter.setOnclickImageListener(object: CardAdapterKotlin.OnClickImageListener{
            override fun onCLickImage(view: View, imageResId: Int) {
                blur_screen_image_background.setImageResource(imageResId)
                currentImageResIdSelected = imageResId
            }

        })
        recycler_view_images_collection.adapter = adapter

        mCardScaleHelper = CardScaleHelperKotlin()
        mCardScaleHelper.setCurrentItemPos(0)
        mCardScaleHelper.attachToRecyclerView(recycler_view_images_collection)

        //notifyBackgroundChange()
    }

    private fun blurBackground(resId: Int, radius: Int){
        GlobalScope.launch(Dispatchers.Main) {
            //delay(1000)
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            ViewSwitchUtils.startSwitchBackgroundAnim(
                baseContext,
                blur_screen_image_background,
                BlurBitmapUtils.getBlurBitmap(blur_screen_image_background.context, bitmap, radius)
            )
        }
    }

    private fun notifyBackgroundChange() {
        if(mLastPos == mCardScaleHelper.getCurrentItemPos()) return
        mLastPos = mCardScaleHelper.getCurrentItemPos()
        val resId = mList[mCardScaleHelper.getCurrentItemPos()]

        GlobalScope.launch(Dispatchers.Main) {
            //delay(1000)
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            ViewSwitchUtils.startSwitchBackgroundAnim(
                baseContext,
                blur_screen_image_background,
                BlurBitmapUtils.getBlurBitmap(blur_screen_image_background.context, bitmap, currentBlurRadius)
            )
        }
    }
}