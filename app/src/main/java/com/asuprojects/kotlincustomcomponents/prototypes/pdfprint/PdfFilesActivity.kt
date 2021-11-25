package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.extensions.OnItemClickListener
import com.asuprojects.kotlincustomcomponents.extensions.addOnItemClickListener
import com.asuprojects.kotlincustomcomponents.helpers.IntentHelper
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_pdf_files.*
import java.io.File

class PdfFilesActivity : AppCompatActivity() {

    private var files = mutableListOf<String>()
    private lateinit var adapter: SimpleItemTextAdapter

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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_pdf_files)

        supportActionBar?.apply {
            this.title = "Pdf Files"
            this.setDisplayHomeAsUpEnabled(true)
        }

        var externalpath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/generatedPdfs"

        // Pastas geradas
        // canvas  -> imagens geradas por canvas
        // droidText  -> pdfs gerados por IText
        // generatedPdfs  -> pdfs gerados combinado com imagens e canvas
        // printedPdfDocument  -> pdfs gerados por printedPdfDocument
        // printedView  -> gerado a partir de uma view

        val folders = mutableListOf<String>(
            "/canvas",
            "/droidText",
            "/generatedPdfs",
            "/printedPdfDocument",
            "/printedView"
        )

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, folders)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pdf_files_spinner_diretorios.adapter = arrayAdapter
        pdf_files_spinner_diretorios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val folder = folders[position]
                externalpath = "${getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath}$folder"

                loadFolders(externalpath)

            }

        }

        val filesPath = filesDir.absolutePath + "/generatedPdfs"

        val dataDirectory = Environment.getDataDirectory().absolutePath
        Log.i("TESTE", "Data Directory: $dataDirectory")
        val externalStorageDirectory = Environment.getExternalStorageDirectory().absolutePath
        Log.i("TESTE", "External Storage Directory: $externalStorageDirectory")

        loadFolders(externalpath)

    }

    private fun loadFolders(folderPath: String){
        files.clear()
        val diretorio = File(folderPath)
        pdf_files_diretorio_principal.text = diretorio.absolutePath
        diretorio.list()?.forEach {
            files.add(it)
        }
        adapter = SimpleItemTextAdapter(files)
        recycler_pdf_files.adapter = adapter
        adapter.setOnClickSimpleItemListener(object: OnClickSimpleItemListener{
            override fun onClickSimpleItem(view: View, position: Int) {
                val filePath = "${diretorio.absolutePath}/${files[position]}"

                val split = filePath.split(".")
                val extensao = split.last()

                Log.i("TESTE", "Extensao: $extensao")

                if(extensao == "pdf"){
                    IntentHelper.openPdf(this@PdfFilesActivity, File(filePath))
                } else if(extensao == "jpg" || extensao == "png" || extensao == "jpeg"){
                    IntentHelper.openImage(this@PdfFilesActivity, File(filePath))
                }

                Log.i("TESTE", "File Path: $filePath")
                //openDocument(filePath)

            }
        })
    }

    private fun openDocument(filePath: String) {
        val file = File(filePath)
        val openDocumentIntent = Intent()
        openDocumentIntent.action = Intent.ACTION_VIEW

        val fileUri = FileProvider.getUriForFile(
            this@PdfFilesActivity,
            "com.asusprojects.kotlincustomcomponents.fileprovider",
            file
        )

        openDocumentIntent.setDataAndType(fileUri, "application/pdf")
        openDocumentIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        //Intent.FLAG_ACTIVITY_NO_HISTORY
        //Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        startActivity(openDocumentIntent)
        return
    }
}
