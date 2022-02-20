package com.asuprojects.kotlincustomcomponents.screens.camerax

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.*
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityCameraXtakePhotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraXTakePhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraXtakePhotoBinding

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var flashMode = FLASH_MODE_AUTO
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private val flashModes = listOf(
        FLASH_MODE_AUTO, FLASH_MODE_ON, FLASH_MODE_OFF
    )
    private var flashModesPosition = 0

    private var flipCamera = false

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraXtakePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraPreviewArrowBack.setOnClickListener {
            onBackPressed()
        }

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this@CameraXTakePhotoActivity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)

        }

        // Set up the listener for take photo button
        binding.btnTakePhoto.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnFlipCamera.setOnClickListener {
            flipCamera = !flipCamera
            if(flipCamera){
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                startCamera()
            }else{
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
        }

        binding.btnFlashMode.setOnClickListener {
            flashModesPosition++
            if(flashModesPosition > (flashModes.size - 1)){
                flashModesPosition = 0
            }
            when(flashModesPosition){
                0 -> {
                    binding.btnFlashMode.setImageResource(R.drawable.ic_flash_auto)
                    flashMode = FLASH_MODE_AUTO
                    startCamera()
                }
                1 -> {
                    binding.btnFlashMode.setImageResource(R.drawable.ic_flash_on)
                    flashMode = FLASH_MODE_ON
                    startCamera()
                }
                2 -> {
                    binding.btnFlashMode.setImageResource(R.drawable.ic_flash_off)
                    flashMode = FLASH_MODE_OFF
                    startCamera()
                }
            }
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                    }
                }

            imageCapture = ImageCapture.Builder()
                .setFlashMode(flashMode)
                .build()

            // Select back camera as a default
            //val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            //val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)


                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {

//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }

        val mediaDir = cacheDir?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}