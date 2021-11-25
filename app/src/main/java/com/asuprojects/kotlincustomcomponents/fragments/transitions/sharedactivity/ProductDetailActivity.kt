package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setSupportActionBar(toolbar_product_details)
        supportActionBar?.apply {
            this.title = ""
            this.setDisplayHomeAsUpEnabled(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(android.R.color.black, null)
        }

        intent?.apply {
            if(hasExtra("PRODUCT")){
                val product = intent.getParcelableExtra<Product>("PRODUCT")
                product?.apply {
                    image_product.setImageResource(resourceId)
                    text_product_title.text = title
                    text_product_description.text = description
                    text_product_specification.text = details
                    text_product_price.text = "R$ $price"
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) supportFinishAfterTransition()
        return super.onOptionsItemSelected(item)
    }
}