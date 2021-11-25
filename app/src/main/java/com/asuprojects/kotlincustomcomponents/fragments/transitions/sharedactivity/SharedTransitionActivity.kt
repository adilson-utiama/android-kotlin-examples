package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_shared_transition.*

class SharedTransitionActivity : AppCompatActivity() {

    private var products = mutableListOf<Product>()
    private lateinit var adapter: TransitionItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_transition)

        setSupportActionBar(toolbar_activity_transition)
        supportActionBar?.apply {
            this.title = ""
            this.setDisplayHomeAsUpEnabled(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(android.R.color.black, null)
        }

        products = ProductCreationHelper.getProducts()

        adapter = TransitionItemAdapter(products)

        recycler_activity_transition.adapter = adapter
        adapter.setOnClickProduct(object: TransitionItemAdapter.OnClickProduct{
            override fun onSelectProduct(view: View, position: Int) {
                val product = products[position]
                val intent =
                    Intent(this@SharedTransitionActivity, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT", product)
//                val image = Pair<View, String>(view, "product_image")
//                val title = Pair<View, String>(view, "product_image")
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@SharedTransitionActivity, view, "product_image"
                    )
                startActivity(intent, options.toBundle())
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}