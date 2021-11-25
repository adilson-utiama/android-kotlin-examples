package com.asuprojects.kotlincustomcomponents.fragments.architecture.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R

class RoomDatabaseActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_room_database)

        supportActionBar?.apply {
            this.title = "Room Database"
            this.setDisplayHomeAsUpEnabled(true)
        }
    }
}