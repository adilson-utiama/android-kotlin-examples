package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.printedodfdoc

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.core.graphics.BitmapCompat
import com.asuprojects.kotlincustomcomponents.extensions.drawMultilineText
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.asuprojects.kotlincustomcomponents.helpers.ValueConversionHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PrintedPdfDocumentHelper {

    companion object {

        fun printPdf(activity: Activity, bitmap: Bitmap): File?{

            //720 px -> 10 inch * 1000 = 10000
            //1188 px -> 16.5 inch * 16500

            val largura = ValueConversionHelper.convertPTtoINCH(bitmap.width.toFloat()) * 1000
            val altura = ValueConversionHelper.convertPTtoINCH(bitmap.height.toFloat()) * 1000

            Log.i("TESTE", "Largura: $largura, Altura: $altura")

            /** ISO A4 media size: 210mm x 297mm (8.27" x 11.69") */
//            public static final MediaSize ISO_A4 =
//                new MediaSize("ISO_A4", "android", R.string.mediasize_iso_a4, 8270, 11690);

            var file: File? = null
            val calendar = Calendar.getInstance()

            val filesDir =
                activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/generatedPdfs"
            val dir = File(filesDir)
            if (!dir.exists()) dir.mkdirs()

            val printAttributes = PrintAttributes.Builder()
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .setMediaSize(PrintAttributes.MediaSize("CustomId", "CustomSize",
                    largura.toInt(), altura.toInt()))
                .setResolution(
                    PrintAttributes.Resolution("pdfdoc",
                        AppCompatActivity.PRINT_SERVICE, 72, 72))
                .setMinMargins(PrintAttributes.Margins(20,20,20,20))
                .build()
            val pdfDocument = PrintedPdfDocument(activity, printAttributes)

            val page = pdfDocument.startPage(0)

            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0.0f,0.0f, null)

//            val imageView = AppCompatImageView(activity)
//            imageView.setImageBitmap(bitmap)
//            imageView.draw(canvas)

            pdfDocument.finishPage(page)

            val os: FileOutputStream
            try{
                file = File(filesDir, "pdf_created_${calendar.timeInMillis}.pdf")

                os = FileOutputStream(file)
                pdfDocument.writeTo(os)
                pdfDocument.close()
                os.close()

            }catch(e: IOException){
                throw IOException("Erro ao gravar arquivo pdf...")
            }

            return file
        }

        fun generatePdf(activity: Activity, view: View, textos: MutableList<String>): File? {

            var file: File? = null
            val calendar = Calendar.getInstance()

            val filesDir =
                activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/printedPdfDocument"
            val dir = File(filesDir)
            if (!dir.exists()) dir.mkdirs()

            val width = view.measuredWidth
            val height = view.measuredHeight

            Log.i("TESTE", "View width: $width / height: $height")

            val printAttributes = PrintAttributes.Builder()
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .setMediaSize(PrintAttributes.MediaSize.ISO_A3)
//                .setMediaSize(PrintAttributes.MediaSize("CustomId", "CustomSize",
//                    ((width / 100) * 1200), ((height / 100) * 2000) ))
                .setResolution(
                    PrintAttributes.Resolution("pdfdoc",
                        AppCompatActivity.PRINT_SERVICE, 72, 72))
                .setMinMargins(PrintAttributes.Margins(20,20,20,20))
                .build()

            val pdfDocument = PrintedPdfDocument(activity, printAttributes)

            //create page description
            //val pageInfo = PdfDocument.PageInfo.Builder(300, 300, 1).create()

            //create a new page from pageInfo
            val page = pdfDocument.startPage(0)

            // repaint the user's text into the page
            //view.draw(page.canvas)

            drawPage(page, textos)

            // do final processing of the page
            pdfDocument.finishPage(page)

            // Here you could add more pages in a longer doc app, but you'd have
            // to handle page-breaking yourself in e.g., write your own word processor...

            // Now write the PDF document to a file; it actually needs to be a file
            // since the Share mechanism can't accept a byte[]. though it can
            // accept a String/CharSequence.
            val os: FileOutputStream
            try{
//                val pdfDirPath = File(filesDir, "pdfs")
//                pdfDirPath.mkdirs()
                file = File(filesDir, "pdf_created_${calendar.timeInMillis}.pdf")

                os = FileOutputStream(file)
                pdfDocument.writeTo(os)
                pdfDocument.close()
                os.close()

                val contentUri: Uri = FileProvider.getUriForFile(
                    activity,
                    "com.asusprojects.kotlincustomcomponents.fileprovider",
                    file)

                shareDocument(
                    activity,
                    contentUri
                )

            }catch(e: IOException){
                throw IOException("Erro ao gravar arquivo pdf...")
            }

            ToastUtil.msg(activity, "Pdf gerado com Sucesso!")

            return file
        }

        private fun drawPage(page: PdfDocument.Page?, textos: MutableList<String>) {

            val canvas = page?.canvas

            var titleBaseLine = 40
            var lefMargin = 20

            val paint = Paint()
            paint.color = Color.BLACK

            paint.textSize = 20f

//        val typeface = Typeface.createFromAsset(context.assets, "font/roboto_medium.ttf")
//        paint.typeface = typeface
            canvas?.drawText("PDF Sample", lefMargin.toFloat() ,titleBaseLine.toFloat(), paint)

            for(texto in textos){
                titleBaseLine += 30
                paint.textSize = 14f
                canvas?.drawText(texto, lefMargin.toFloat() , titleBaseLine.toFloat(), paint)
            }

//            paint.textSize = 12f
//            canvas?.drawText("Pdf line 2", lefMargin.toFloat() ,titleBaseLine.toFloat() + 50, paint)
//
//            paint.textSize = 14f
//            canvas?.drawText("Pdf line 3", lefMargin.toFloat() ,titleBaseLine.toFloat() + 80, paint)
//
//            paint.textSize = 12f
//            canvas?.drawText("Pdf line 4", lefMargin.toFloat() ,titleBaseLine.toFloat() + 100, paint)
//
//            paint.textSize = 14f
//            canvas?.drawText("Pdf line 5", lefMargin.toFloat() ,titleBaseLine.toFloat() + 130, paint)

        }

        private fun shareDocument(activity: Activity, uri: Uri) {
            val mShareIntent = Intent()
            mShareIntent.action = Intent.ACTION_SEND
            mShareIntent.type = "application/pdf"
            // Assuming it may go via eMail:
            //mShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Here is a PDF from PdfSend")
            // Attach the PDf as a Uri, since Android can't take it as bytes yet.
            mShareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            mShareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            activity.startActivity(mShareIntent)
            return
        }

    }
}