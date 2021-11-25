package com.asuprojects.kotlincustomcomponents.fragments.pickers.filepicker

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_file_picker.*
import java.io.FileDescriptor
import java.io.IOException

class FilePickerActivity : AppCompatActivity() {

    companion object {
        const val CREATE_FILE = 1
        const val PICK_FILE = 2
        const val CHOOSE_FOLDER = 3
    }

    private lateinit var mContentResolver : ContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker)

        supportActionBar?.let {
            it.title = "File Picker"
            it.setDisplayHomeAsUpEnabled(true)
        }

        mContentResolver = applicationContext.contentResolver

        val initialDirectory = Environment.DIRECTORY_DOWNLOADS

        text_file_picker_initial_uri.text = initialDirectory.toUri().toString()

        btn_file_picker_open_file.setOnClickListener {
            openFile(initialDirectory.toUri())
        }

        btn_file_picker_create_file.setOnClickListener {
            createFile(initialDirectory.toUri())
        }

        btn_file_picker_choose_folder.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                openDirectory(initialDirectory.toUri())
            }
        }
    }

    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            //type = "application/pdf"
            type = "document/*"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }
        startActivityForResult(intent, CREATE_FILE)
    }

    private fun openFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            //type = "application/pdf"
            type = "*/*"
            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }

        startActivityForResult(intent, PICK_FILE)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun openDirectory(pickerInitialUri: Uri) {
        // Choose a directory using the system's file picker.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            // Provide read access to files and sub-directories in the user-selected
            // directory.
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker when it loads.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }

        startActivityForResult(intent, CHOOSE_FOLDER)
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor =
            mContentResolver.openFileDescriptor(uri, "r")!!
        val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PICK_FILE){
            data?.let {
                val dataUri = it.data
                Log.i("TESTE", ">>> Data: $dataUri")
                text_file_picker_uri_selected.text = dataUri.toString()
            }
        }
        if(requestCode == CREATE_FILE){
            data?.let {
                val dataUri = it.data
                Log.i("TESTE", ">>> Data: $dataUri")
                text_file_picker_uri_selected.text = dataUri.toString()
            }
        }
        if(requestCode == CHOOSE_FOLDER){
            data?.let {
                val dataUri = it.data
                Log.i("TESTE", ">>> Data: $dataUri")
                text_file_picker_uri_selected.text = dataUri.toString()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}