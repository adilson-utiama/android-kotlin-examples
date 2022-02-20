package com.asuprojects.kotlincustomcomponents.screens.camerax

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityCameraXexamplesBinding

class CameraXExamplesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraXexamplesBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityCameraXexamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "CameraX Examples"
        }

        binding.cameraXTakePhoto.setOnClickListener {
            startActivity(Intent(this@CameraXExamplesActivity, CameraXTakePhotoActivity::class.java))
            overridePendingTransition(R.anim.fast_fade_in, R.anim.fast_fade_out)
        }

    }
}