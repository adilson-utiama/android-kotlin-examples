package com.asuprojects.kotlincustomcomponents.screens.qrcode

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityReadQrcodeBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.ScanContract




class ReadQRCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadQrcodeBinding

//    // Register the launcher and result handler
//    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
//        ScanContract()
//    ) { result ->
//        if (result.getContents() == null) {
//            Toast.makeText(this@ReadQRCodeActivity, "Cancelled", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this@ReadQRCodeActivity, "Scanned: " + result.getContents(), Toast.LENGTH_LONG)
//                .show()
//        }
//    }
//
//    // Launch
//    fun onButtonClick(view: View?) {
//        barcodeLauncher.launch(ScanOptions())
//    }

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
        binding = ActivityReadQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scan QR Code"
        }

        binding.btnLeitura.setOnClickListener{
            val scanner = IntentIntegrator(this)
            scanner.captureActivity = AnyOrientationCaptureActivity::class.java
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false) //retira o beep ao scannear
            scanner.setOrientationLocked(false)
            scanner.initiateScan() // `this` is the current Activity

//            val scanOptions = ScanOptions()
//            scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//            scanOptions.setPrompt("Scan a QR Code");
//            scanOptions.setCameraId(0) // Use a specific camera of the device
//            scanOptions.setBeepEnabled(false)
//            scanOptions.setBarcodeImageEnabled(true)
//            scanOptions.setOrientationLocked(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    binding.textResult.text = result.contents
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}