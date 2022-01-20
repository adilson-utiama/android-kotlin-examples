package com.asuprojects.kotlincustomcomponents.screens.qrcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.MenuItem
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.ActivityGenerateQrcodeBinding
import com.asuprojects.kotlincustomcomponents.fragments.pickers.filepicker.FilePickerActivity
import com.asuprojects.kotlincustomcomponents.helpers.FileManager
import com.asuprojects.kotlincustomcomponents.helpers.FileUtilsKotlin
import com.asuprojects.kotlincustomcomponents.helpers.PermissionsHelper
import kotlinx.android.synthetic.main.activity_file_picker.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.lang.Exception
import java.util.*

class GenerateQRCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateQrcodeBinding

    private var bitmap: Bitmap? = null
    private var qrgEncoder: QRGEncoder? = null
    private var fileName: String = ""

    private val CREATE_FILE = 250

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Generate QR Code"
        }

        binding.btnGenerateQrcode.setOnClickListener {
            val permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val valid = PermissionsHelper.validatePermissions(this@GenerateQRCodeActivity, permissions, 500)
            if(valid){
                Toast.makeText(this@GenerateQRCodeActivity, "Permissões Concedidas!!", Toast.LENGTH_SHORT).show()
                generateQRCode()
            }
        }

        binding.btnGenerateQrcodeImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createFile()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            500 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(this@GenerateQRCodeActivity, "Necessário permitir a leitura e leitura para utilizar este recurso", Toast.LENGTH_SHORT).show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun generateQRCode() {
        val text = binding.textInput.text.toString().trim()
        if (text.isEmpty()) {
            Toast.makeText(
                this@GenerateQRCodeActivity,
                "Enter some text to generate QR code",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            //getting he windowmanager service
            val manager = getSystemService(WINDOW_SERVICE) as WindowManager

            //initializing a variable for default display
            val display = manager.defaultDisplay

            //creating a variable for point
            //which isto be displayed in QR code
            val point = Point()
            display.getSize(point)

            //getting width and height of a point
            val width = point.x
            val height = point.y

            //generating dimension from width and height
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            //setting this dimension inside our qr code
            //encode to generate our qr code
            qrgEncoder = QRGEncoder(text, null, QRGContents.Type.TEXT, dimen)
            try {
                //getting our qr code in the form of bitmap
                bitmap = qrgEncoder?.bitmap

                binding.qrcodeImageview.setImageBitmap(bitmap)

                binding.btnGenerateQrcodeImage.visibility = VISIBLE
            } catch (e: Exception) {
                Toast.makeText(
                    this@GenerateQRCodeActivity,
                    "Generate QR Code Fail!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun createFile(pickerInitialUri: Uri? = null) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            //type = "application/pdf"
            //type = "text/plain"
            type = "image/jpeg"

            val calendar = Calendar.getInstance()
            fileName = "Generated QRCode - ${calendar.timeInMillis}"

            putExtra(Intent.EXTRA_TITLE, fileName)
            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            pickerInitialUri?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }
            }

        }

        GlobalScope.launch(Dispatchers.IO) {
            bitmap?.let {
                val qrcSaver = QRGSaver()
                val cache = File(cacheDir, "qrcodes")

                val location = "${cacheDir.absolutePath}/"
                qrcSaver.save(location, fileName, it, QRGContents.ImageType.IMAGE_JPEG)
            }

        }
        startActivityForResult(intent, CREATE_FILE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CREATE_FILE){
            data?.let {
                val dataUri = it.data
                val cal = Calendar.getInstance()
                try{
                    Log.i("TESTE", ">>> DataUri > $dataUri")

//                    val qrcSaver = QRGSaver()
//                    qrcSaver.save(cacheDir.absolutePath, fileName, bitmap, QRGContents.ImageType.IMAGE_JPEG)
                    //val cache = File(cacheDir, "qrcodes")
                    val imageFile = File(cacheDir, "$fileName.jpg")

                    //val inputStream = contentResolver.openInputStream(Uri.fromFile(imageFile))
                    val outputStream = contentResolver.openOutputStream(dataUri!!)

                    var inputStream: InputStream? = null
                    var bufferedOutputStream: BufferedOutputStream? = null
                    try {
                        inputStream = contentResolver.openInputStream(Uri.fromFile(imageFile))
                        bufferedOutputStream = BufferedOutputStream(outputStream)
                        val buf = ByteArray(1024)
                        inputStream!!.read(buf)
                        do {
                            bufferedOutputStream.write(buf)
                        } while (inputStream.read(buf) != -1)
                    } catch (e: IOException) {
                        Log.e("ERROR", ">>> Error: ${e.message}")
                    } finally {
                        try {
                            inputStream?.close()
                            bufferedOutputStream?.close()
                        } catch(e: IOException) {
                            Log.e("ERROR", ">>> Error: ${e.message}")
                        }
                    }

                    //FileManager.saveFileFromUri(this@GenerateQRCodeActivity, dataUri!!, cacheDir)

//                    val realPath = FileUtilsKotlin.getRealPathFromURI(this@GenerateQRCodeActivity, dataUri!!)
//                    Log.i("TESTE", ">>> Real Path > $realPath")

                }catch(e: Exception){
                    Toast.makeText(this@GenerateQRCodeActivity, "Erro ao gerar Imagem: ${e.message}", Toast.LENGTH_LONG).show()
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}