package com.asuprojects.kotlincustomcomponents.fragments.media.image

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.IntentHelper
import com.asuprojects.kotlincustomcomponents.helpers.MyFilesUtil
import com.asuprojects.kotlincustomcomponents.helpers.PermissionsHelper
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_images.*
import java.io.File
import java.io.IOException
import java.util.*


class ImagesActivity : AppCompatActivity() {

    private val PICK_IMAGE = 200
    private val CAMERA_REQUEST_CODE = 300
    private val TAKE_PHOTO_PERMISSION = 400
    private val WRITE_EXTERNAL_STORAGE_PERMISSION = 500

    private var cameraFilePath = ""

    private var imageUri: Uri? = null

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
        setContentView(R.layout.activity_images)

        //PermissionsHelper.requestCameraPermission(this, TAKE_PHOTO_PERMISSION)
        PermissionsHelper.requestWriteStoragePermission(this, WRITE_EXTERNAL_STORAGE_PERMISSION)

        supportActionBar?.apply {
            this.title = "Images"
            this.setDisplayHomeAsUpEnabled(true)
        }

        image_load_image.setOnClickListener {
            imageUri?.apply {
                try{
                    IntentHelper.showPhoto(this@ImagesActivity, this)
                }catch(e: Exception){
                    ToastUtil.msg(this@ImagesActivity, "Não foi possivel vizualizar a image")
                }
            }
        }

        btn_pick_image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)

        }

        btn_take_photo.setOnClickListener {

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {takeIntentPhoto ->
                takeIntentPhoto.resolveActivity(packageManager)?.also {
                    val imageFile: File? = try {
                        createImageFile()
                    } catch (e: IOException) {
                        Log.i("TESTE", "Error: ${e.message}")
                        null
                    }
                    imageFile?.also {
                        val imageUri: Uri = FileProvider.getUriForFile(
                            this@ImagesActivity,
                            "com.asusprojects.kotlincustomcomponents.fileprovider",
                            it
                        )
                        takeIntentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        startActivityForResult(takeIntentPhoto, CAMERA_REQUEST_CODE)
                    }
                }
            }

//            try{
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                intent.putExtra(
//                    MediaStore.EXTRA_OUTPUT,
//                    FileProvider.getUriForFile(
//                        this@ImagesActivity,
//                        "com.asusprojects.kotlincustomcomponents.fileprovider",
//                        createImageFile()
//                    )
//                )
//                startActivityForResult(intent, CAMERA_REQUEST_CODE)
//            }catch(e: IOException){
//                e.printStackTrace()
//                Log.i("TESTE", "Error: ${e.message}")
//            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            when(requestCode){
                PICK_IMAGE -> {
                    val uri: Uri? = data?.data
                    uri?.apply {
                        imageUri = this
                        image_load_image.setImageURI(this)
                        getImageInfo(this)
                    }
                }
                CAMERA_REQUEST_CODE -> {
                    val uri: Uri? = data?.data
                    uri?.apply {
                        imageUri = this
                        image_load_image.setImageURI(Uri.parse(cameraFilePath))
                        getImageInfo(File(cameraFilePath))
                    }
                }
            }
        }

    }

    private fun getImageInfo(imageFile: File?){
        text_image_uri.text = "No Uri"
        text_image_filepath.text = imageFile?.absolutePath

        imageFile?.apply {
            val decodeFile = BitmapFactory.decodeFile(imageFile.absolutePath)

            val width = decodeFile.width
            val height = decodeFile.height

            text_image_dimension.text = "$width x $height"
        }
    }

    private fun getImageInfo(uri: Uri){
        //val filePath = FilePathResolver.getPath(this@ImagesActivity, uri)
        val filePath = MyFilesUtil.getPath(this@ImagesActivity, uri)


        text_image_uri.text = uri.toString()
        text_image_filepath.text = filePath

        val inputStream = contentResolver.openInputStream(uri)
        val decodeFile = BitmapFactory.decodeStream(inputStream)

        val width = decodeFile.width
        val height = decodeFile.height

        text_image_dimension.text = "$width x $height"
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val calendar = Calendar.getInstance()
        val stringFileName = "image_${calendar.timeInMillis}_"
        //val storageDir = File(getExternalFilesDir(Environment.DIRECTORY_DCIM), "Camera")
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM)
        Log.i("TESTE", "StorageDir: $storageDir")
        return File.createTempFile(
            stringFileName, //prefix
            ".jpg", //suffix
            storageDir //directory
        ).apply {
            cameraFilePath = this.absolutePath
            Log.i("TESTE", "Image File: ${this.absolutePath}")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            TAKE_PHOTO_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.msg(this@ImagesActivity, "Permission granted!! Now you can use Camera")
                } else {
                    ToastUtil.msg(this@ImagesActivity, "Oops, You just denied the Camera Permission!")
                }
            }
            WRITE_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.msg(this@ImagesActivity, "Permission granted!! Now you write external storage")
                } else {
                    ToastUtil.msg(this@ImagesActivity, "Oops, You just denied to write external storage Permission!")
                }
            }
        }

    }
}
