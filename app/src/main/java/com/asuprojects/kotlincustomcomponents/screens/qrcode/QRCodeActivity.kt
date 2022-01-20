package com.asuprojects.kotlincustomcomponents.screens.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityQrcodeBinding

class QRCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrcodeBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "QR Codes"
        }

        binding.qrcodeScreenBtnGenerate.setOnClickListener {
            startActivity(Intent(this@QRCodeActivity, GenerateQRCodeActivity::class.java))
        }

        binding.qrcodeScreenBtnRead.setOnClickListener {
            startActivity(Intent(this@QRCodeActivity, ReadQRCodeActivity::class.java))
        }

    }
}