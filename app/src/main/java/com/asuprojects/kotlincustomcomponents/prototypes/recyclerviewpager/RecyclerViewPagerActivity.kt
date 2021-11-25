package com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.adapter.MyPagerAdapter
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.adapter.MyPagerViewHolder
import com.asuprojects.kotlincustomcomponents.prototypes.recyclerviewpager.adapter.PageModel
import kotlinx.android.synthetic.main.activity_recycler_view_pager.*

class RecyclerViewPagerActivity : AppCompatActivity() {

    private val mModels = listOf(
        PageModel(Color.rgb(0xFD, 0xBC, 0x5F)),
        PageModel(Color.rgb(242, 101, 49)),
        PageModel(Color.rgb(233, 119, 175)),
        PageModel(Color.rgb(117, 129, 191)),
        PageModel(Color.rgb(114, 204, 210)),
        PageModel(Color.rgb(200, 223, 142))
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_pager)

        supportActionBar?.apply {
            title = "RecyclerView PagerView"
            setDisplayHomeAsUpEnabled(true)
        }

        recycler_view_pager.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = MyPagerAdapter(mModels)
        recycler_view_pager.adapter = adapter

        MyPagerSnapHelperListenable().attachToRecyclerView(recycler_view_pager, object: MyPagerStateListener{
            override fun onPageScroll(pagesState: List<VisiblePageState>) {
                Log.i("TESTE", ">>> onPageScroll....")
                for (pageState in pagesState) {
                    val vh = recycler_view_pager.findContainingViewHolder(pageState.view) as MyPagerViewHolder
                    vh.setRealtimeAttr(pageState.index, pageState.viewCenterX.toString(), pageState.distanceToSettled, pageState.distanceToSettledPixels)
                    Log.i("TESTE", ">>> Page: ${pageState.index}, HorizRate: ${pageState.distanceToSettled}, HorizPx: ${pageState.distanceToSettledPixels}")
                }
            }
            override fun onScrollStateChanged(state: MyPageScrollState) {}
            override fun onPageSelected(index: Int) {
                val vh = recycler_view_pager.findViewHolderForAdapterPosition(index) as MyPagerViewHolder?
                vh?.onSelected()
            }
        })

//        val cardWidthPixels = (resources.displayMetrics.widthPixels * 0.80f).toInt()
//        val cardHintPercent = 0.01f
//        recycler_view_pager.addItemDecoration(MyPagerSnapFancyDecorator(this, cardWidthPixels, cardHintPercent))


    }
}
