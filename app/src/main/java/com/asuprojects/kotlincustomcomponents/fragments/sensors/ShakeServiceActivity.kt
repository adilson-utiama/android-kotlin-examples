package com.asuprojects.kotlincustomcomponents.fragments.sensors

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R

class ShakeServiceActivity : AppCompatActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var tvShakeService: AppCompatTextView
    }

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
        setContentView(R.layout.activity_shake_service)

        supportActionBar?.apply {
            this.title = "Shake Service"
            this.setDisplayHomeAsUpEnabled(true)
        }

        tvShakeService = findViewById(R.id.tvShakeService)

        val intent = Intent(this, ShakeService::class.java)
        startService(intent)
    }
}
