package com.asuprojects.kotlincustomcomponents.prototypes.recyclercarousel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_recycler_pager_snap_carrousel.*
import kotlinx.android.synthetic.main.viewholder_recycler_pager_snap_carousel.view.*

class RecyclerPagerSnapCarouselActivity : AppCompatActivity() {

    private val carouselAdapter by lazy {
        CarouselAdapter()
    }

    private val images = mutableListOf(
        CarouselEntity(R.drawable.paisagem_praia),
        CarouselEntity(R.drawable.paisagem001),
        CarouselEntity(R.drawable.paisagem002),
        CarouselEntity(R.drawable.paisagem003),
        CarouselEntity(R.drawable.paisagem004),
        CarouselEntity(R.drawable.paisagem005),
        CarouselEntity(R.drawable.paisagem006),
        CarouselEntity(R.drawable.paisagem007),
        CarouselEntity(R.drawable.paisagem008),
        CarouselEntity(R.drawable.paisagem009)
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_pager_snap_carrousel)

        supportActionBar?.apply {
            title = "Carousel"
            setDisplayHomeAsUpEnabled(true)
        }

        recycler_carousel_auto_scrollable.run {
            PagerSnapHelper().attachToRecyclerView(this)
            adapter = carouselAdapter.apply {
                setItems(images)
            }
            addItemDecoration(DotIndicatorDecoration(this@RecyclerPagerSnapCarouselActivity))
            resumeAutoScroll()
        }

        recycler_carousel_non_scrollable.run {
            PagerSnapHelper().attachToRecyclerView(this)
            adapter = carouselAdapter.apply {
                setItems(images)
            }
        }


    }


    data class CarouselEntity(val imgResource: Int)

    class CarouselAdapter() : RecyclerView.Adapter<CarouselHolder>() {

        private var images = mutableListOf<CarouselEntity>()

        val actualItemCount
            get() = images.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_recycler_pager_snap_carousel, parent, false)
                .let {
                    CarouselHolder(it)
                }

        override fun getItemCount(): Int = images.size

        override fun onBindViewHolder(holder: CarouselHolder, position: Int) {
            val image = images[position]
            holder.carouselImage.setImageResource(image.imgResource)
        }

        fun setItems(value: MutableList<CarouselEntity>) {
            images = value
            notifyDataSetChanged()
        }
    }

    class CarouselHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val carouselImage by lazy {
            itemView.vh_carousel_image
        }
    }
}