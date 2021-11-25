package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.playmediaservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_play_media.*

class PlayMediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_media)

        supportActionBar?.apply {
            this.title = "Play Media Service"
            this.setDisplayHomeAsUpEnabled(true)
        }

        btn_start_service.setOnClickListener {
            startService(Intent(baseContext, PlayMediaService::class.java))
        }

        btn_stop_service.setOnClickListener {
            stopService(Intent(baseContext, PlayMediaService::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
