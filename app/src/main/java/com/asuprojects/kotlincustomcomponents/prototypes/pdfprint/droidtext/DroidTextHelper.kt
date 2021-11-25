package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.droidtext

import android.content.Context
import android.os.Environment
import android.util.Log
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfWriter
import com.lowagie.text.pdf.draw.LineSeparator
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class DroidTextHelper {

    companion object {

        fun createPDF(context: Context, contentList: MutableList<String>): File? {

            val calendar = Calendar.getInstance()

            val doc = Document()
            doc.setMargins(20F, 20F, 25F, 25F)
            doc.pageSize = PageSize.A4
            //doc.pageSize = Rectangle(210.0f, 297.0f)

            var file: File? = null
            try {
                //val path: String = context.filesDir.absolutePath.toString() + "/droidText"
                val path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/droidText"

                val dir = File(path)
                if (!dir.exists()) dir.mkdirs()

                Log.d("PDFCreator", "PDF Path: $path")

                file = File(dir, "doc_${calendar.timeInMillis}.pdf")
                val fOut = FileOutputStream(file)
                PdfWriter.getInstance(doc, fOut)

                //open the document
                doc.open()
                val p1 = Paragraph("My First PDF using DroidText")
                val paraFont = Font(Font.COURIER, 24.0f, Font.BOLD)
                paraFont.color = harmony.java.awt.Color.BLUE
                p1.alignment = Paragraph.ALIGN_CENTER
                p1.font = paraFont

                //add paragraph to document
                doc.add(p1)

                doc.add(Paragraph(" "))

                val lineSeparator = LineSeparator()
                lineSeparator.lineColor = harmony.java.awt.Color.GREEN
                doc.add(lineSeparator)

                for (text in contentList){
                    val paragraph = Paragraph(text)
                    val parFont = Font(Font.COURIER, 20.0f, Font.NORMAL)
                    paragraph.alignment = Paragraph.ALIGN_LEFT
                    paragraph.keepTogether = true
                    paragraph.font = parFont

                    doc.add(paragraph)

                    doc.add(Paragraph(" "))
                }

//                val p2 = Paragraph("This is an example of a simple paragraph")
//                val paraFont2 = Font(Font.COURIER, 14.0f, Color.GREEN)
//                p2.alignment = Paragraph.ALIGN_CENTER
//                p2.font = paraFont2
//                doc.add(p2)

                //Adiciona uma imagem
//                val stream = ByteArrayOutputStream()
//                val bitmap = BitmapFactory.decodeResource(
//                    context.resources,
//                    R.drawable.senery
//                )
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//
//                val myImg: Image = Image.getInstance(stream.toByteArray())
//                myImg.alignment = Image.MIDDLE
//
//                //add image to document
//                doc.add(myImg)

                //set footer
                val footerText = Phrase("This is an example of a footer")
                val pdfFooter = HeaderFooter(footerText, false)
                doc.setFooter(pdfFooter)

                ToastUtil.msg(context, "Pdf gerado com Sucesso!")

            } catch (de: DocumentException) {
                Log.e("PDFCreator", "DocumentException:$de")
            } catch (e: IOException) {
                Log.e("PDFCreator", "ioException:$e")
            } finally {
                doc.close()
            }

            return file
        }


    }
}