package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_shared_fragment.*

class SharedFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_fragment)

        supportActionBar?.apply {
            this.title = "Fragment Transition"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val tx = supportFragmentManager.beginTransaction()
        tx.add(R.id.framelayout_weapons, WeaponsGridFragment())
        tx.commit()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}