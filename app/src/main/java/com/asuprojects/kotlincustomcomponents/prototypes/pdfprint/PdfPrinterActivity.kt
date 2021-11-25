package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.print.PrintManager
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.extensions.getWholeRecyclerView
import com.asuprojects.kotlincustomcomponents.helpers.ImageHelper
import com.asuprojects.kotlincustomcomponents.helpers.IntentHelper
import com.asuprojects.kotlincustomcomponents.helpers.RandomValues
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.canvas.CanvasPrintHelper
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.droidtext.DroidTextHelper
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.printedodfdoc.MyPrintDocumentAdapter
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.printedodfdoc.PrintedPdfDocumentHelper
import com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.viewtobitmap.ViewToBitmapHelper
import kotlinx.android.synthetic.main.activity_pdf_printer.*
import java.io.File
import java.lang.StringBuilder


class PdfPrinterActivity : AppCompatActivity() {

    private var textos = mutableListOf<String>()
    private lateinit var adapter: SimpleTextAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_pdf_printer)

        supportActionBar?.apply {
            title = "Pdf Printer"
            setDisplayHomeAsUpEnabled(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, null)
        }

        textos = RandomValues.getTexts()

        adapter = SimpleTextAdapter(textos)
        recycler_print_pdf.adapter = adapter

        print_pdf_btn_add_text.setOnClickListener {
            val newText = print_pdf_input_text.text.toString()
            if(newText.isNotEmpty()){
                textos.add(newText)
                adapter.notifyDataSetChanged()

                print_pdf_input_text.text?.clear()
            }else{
                ToastUtil.msg(baseContext, "Nenhum conteudo adicionado...")
            }
        }

        print_pdf_btn_generate_doc.setOnClickListener {

            val popup = PopupMenu(this@PdfPrinterActivity, it)
            popup.menuInflater.inflate(R.menu.pop_menu_pdf_print, popup.menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.popmenu_option_droidtext -> {
                        AlertDialog.Builder(this@PdfPrinterActivity)
                            .setTitle("Gerar Pdf com DrodText?")
                            .setPositiveButton("Gerar"){_,_ ->
                                run {
                                    //generatePdf()
                                    val file = DroidTextHelper.createPDF(this@PdfPrinterActivity, textos)

                                    Log.i("TESTE", "File Path: ${file?.absolutePath}")
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                    R.id.popmenu_option_pdfprinter -> {
                        AlertDialog.Builder(this@PdfPrinterActivity)
                            .setTitle("Gerar Pdf com PrintedPdfDocument?")
                            .setPositiveButton("Gerar"){_,_ ->
                                run {
                                    //generatePdf()
                                    val file = PrintedPdfDocumentHelper.generatePdf(this@PdfPrinterActivity,
                                        recycler_print_pdf, textos)

                                    Log.i("TESTE", "File Path: ${file?.absolutePath}")

                                    //printDocument()
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                    R.id.popmenu_option_viewtoimage -> {
                        AlertDialog.Builder(this@PdfPrinterActivity)
                            .setTitle("Gerar Imagem a partir da View?")
                            .setPositiveButton("Gerar"){_,_ ->
                                run {

                                    //testGetRecyclerViewScreenshot()
                                    testRecyclerScreenshoot()
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                    R.id.popmenu_option_canvas -> {
                        AlertDialog.Builder(this@PdfPrinterActivity)
                            .setTitle("Gerar Imagem utilizando Canvas?")
                            .setPositiveButton("Gerar"){_,_ ->
                                run {

                                    testmultilineTextWithCanvas()
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                }
                true
            }
            popup.show()

        }

        print_pdf_btn_view_files.setOnClickListener {
            startActivity(Intent(this@PdfPrinterActivity, PdfFilesActivity::class.java))
        }

        print_pdf_btn_clear_list.setOnClickListener {
            AlertDialog.Builder(this@PdfPrinterActivity)
                .setTitle("Resetar Lista?")
                .setPositiveButton("Resetar"){_,_ ->
                    textos.clear()
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

    }

    private fun testmultilineTextWithCanvas() {

        val filesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/canvas"
        val dir = File(filesDir)
        if (!dir.exists()) dir.mkdirs()

        val bitmap = CanvasPrintHelper.drawMultilineTextToBitmap(
            this@PdfPrinterActivity, textos)
        bitmap?.apply {
            val file =
                ImageHelper.compressBitmapToImagePNG(this, 90, filesDir)

            file?.let {
                val decodeFile = BitmapFactory.decodeFile(file.absolutePath)

                val printPdf =
                    PrintedPdfDocumentHelper.printPdf(this@PdfPrinterActivity, decodeFile)

                IntentHelper.openPdf(this@PdfPrinterActivity, printPdf!!)
            }
        }
    }

    private fun testRecyclerScreenshoot(){
        recycler_print_pdf.getWholeRecyclerView<SimpleTextAdapter.SimpleTextViewHolder> {
            val file = File(it)
            IntentHelper.openImage(this@PdfPrinterActivity, file)
        }
    }

    private fun testGetRecyclerViewScreenshot(){
        val filesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/printedViews"
        val dir = File(filesDir)
        if (!dir.exists()) dir.mkdirs()
        val bitmap =
            ViewToBitmapHelper.getRecyclerViewScreenshot(recycler_print_pdf)
        bitmap?.let {
            val file =
                ImageHelper.compressBitmapToImageJPEG(
                    it,
                    70,
                    filesDir
                )
            file?.let {f ->
                IntentHelper.openImage(this@PdfPrinterActivity, f)
            }
        }
    }

    private fun testGetBitmapFromView1(){
        val filesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/printedViews"
        val dir = File(filesDir)
        if (!dir.exists()) dir.mkdirs()
        val bitmap =
            ViewToBitmapHelper.getBitmapFromView1(recycler_print_pdf)
        bitmap?.let {
            val file =
                ImageHelper.compressBitmapToImageJPEG(
                    it,
                    70,
                    filesDir
                )
            file?.let {f ->
                IntentHelper.openImage(this@PdfPrinterActivity, f)
            }
        }
    }

    private fun printDocument(){
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager?

        printManager?.print("printJobName", MyPrintDocumentAdapter(this@PdfPrinterActivity), null)
    }


}
