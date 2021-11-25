package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.printedodfdoc

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MyPrintDocumentAdapter(val context: Context): PrintDocumentAdapter() {

    private var pageWidth: Int = 0
    private var pageHeight: Int = 0
    private var pdfDocument: PdfDocument? = null
    private var totalPages = 1
    private val calendar = Calendar.getInstance()


    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {

        newAttributes?.apply {
            pdfDocument = PrintedPdfDocument(context, this)
            pageWidth = this.mediaSize!!.widthMils / 1000 * 72
            pageHeight = this.mediaSize!!.heightMils / 1000 * 72
        }

        if(cancellationSignal!!.isCanceled) {
            callback?.onLayoutCancelled()
            return
        }

        if(totalPages > 0){
            val builder = PrintDocumentInfo
                .Builder("print_out_${calendar.timeInMillis}.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(totalPages)
            val documentInfo = builder.build()
            callback?.onLayoutFinished(documentInfo, true)
        }else{
            callback?.onLayoutFailed("Page Count is Zero.")
        }


    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {

        for(i in 0..totalPages){

            Log.i("TESTE", "pagesSize: ${pages!!.size}")

            if(pageInRange(pages, i)){
                val newPage = PdfDocument.PageInfo.Builder(
                    pageWidth, pageHeight, i
                ).create()

                val page = pdfDocument?.startPage(newPage)

                cancellationSignal?.apply {
                    if(this.isCanceled){
                        callback?.onWriteCancelled()
                        pdfDocument?.close()
                        pdfDocument = null
                        return
                    }
                }

                drawPage(page, i)
                pdfDocument?.finishPage(page)
            }
        }

        try {

            saveFile("pdf_created>${calendar.timeInMillis}", pdfDocument!!)

        }catch(e: IOException){
            callback?.onWriteFailed(e.message)
        }finally {
            pdfDocument?.close()
            pdfDocument = null
        }

        callback?.onWriteFinished(pages)
    }

    private fun saveFile(fileName: String, pdfDocument: PdfDocument){
        val file = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS),"$fileName.pdf")


        Log.i("TESTE", "FilePathResolver: ${file.absolutePath}")

        pdfDocument.writeTo(FileOutputStream(file))
        //pdfDocument.close()
    }

    private fun drawPage(page: PdfDocument.Page?, pageNumber: Int) {

        val canvas = page?.canvas

        pageNumber.plus(1)

        val titleBaseLine = 90
        val lefMargin = 50

        val paint = Paint()
        paint.color = Color.BLACK

        paint.textSize = 18f

//        val typeface = Typeface.createFromAsset(context.assets, "font/roboto_medium.ttf")
//        paint.typeface = typeface
        canvas?.drawText("PDF Sample", lefMargin.toFloat() ,titleBaseLine.toFloat(), paint)

        paint.textSize = 14f
        canvas?.drawText("Pdf line 1", lefMargin.toFloat() ,titleBaseLine.toFloat() + 30, paint)

        paint.textSize = 12f
        canvas?.drawText("Pdf line 2", lefMargin.toFloat() ,titleBaseLine.toFloat() + 50, paint)

        paint.textSize = 14f
        canvas?.drawText("Pdf line 3", lefMargin.toFloat() ,titleBaseLine.toFloat() + 80, paint)

        paint.textSize = 12f
        canvas?.drawText("Pdf line 4", lefMargin.toFloat() ,titleBaseLine.toFloat() + 100, paint)

        paint.textSize = 14f
        canvas?.drawText("Pdf line 5", lefMargin.toFloat() ,titleBaseLine.toFloat() + 130, paint)

    }

    private fun pageInRange(pageRanges: Array<out PageRange>, page: Int): Boolean{

        Log.i("TESTE", "Page: $page, PageRanges: ${pageRanges.size}")

        val size = pageRanges.size - 1
        for(i in 0..size){

            Log.i("TESTE", "Index: $i")
            if((page >= pageRanges[i].start) && (page <= pageRanges[i].end)) return true
        }
        return false
    }
}