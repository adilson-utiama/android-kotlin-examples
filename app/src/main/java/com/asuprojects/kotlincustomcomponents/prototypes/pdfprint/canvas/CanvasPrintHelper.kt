package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.canvas

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.view.WindowManager
import java.lang.StringBuilder

class CanvasPrintHelper {

    companion object {

        fun drawMultilineTextToBitmap(gContext: Context, textos: MutableList<String>): Bitmap? {

            val manager = gContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y

            //Preparando texto
            val builder = StringBuilder()
            textos.forEach {
                builder.append(it).append("\n").append("\n")
            }
            val gText = builder.toString()

            // Prepare canvas
            val resources = gContext.resources
            val scale = resources.displayMetrics.density

            val spacos = textos.size * 14
            Log.i("TESTE", "DisplayWidth: $width, DisplayHeight: $height, Spacos: $spacos")
            Log.i("TESTE", "Scale Density: $scale")

            var bitmap = Bitmap.createBitmap(width, (height + spacos), Bitmap.Config.ARGB_8888)
            //var bitmap = BitmapFactory.decodeResource(resources, resId)

            var bitmapConfig = bitmap.config
            // Set Default bitmap config id none
            if(bitmapConfig != null){
                bitmapConfig = Bitmap.Config.ARGB_8888
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true)

            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)

            // new antialiased Paint
            val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

            // text size in pixels
            textPaint.textSize = (14 * scale)

            // text shadow
            //textPaint.setShadowLayer(1f, 0f, 1f, Color.WHITE)

            // set text width to canvas width minus 16dp padding
            val textWidth = (canvas.width - (16 * scale)).toInt()

            // init StaticLayout for text
            val textLayout: StaticLayout
            textLayout = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val builder = StaticLayout.Builder.obtain(gText, 0, gText.length,
                    textPaint, textWidth)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0.0f, 1.0f)
                    .setIncludePad (false)
                builder.build()
            } else {
                StaticLayout(gText, textPaint, textWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
            }

            // get height of multiline text
            val textHeight = textLayout.height + 25

            // get position of text's top left corner
            val x = (bitmap.width - textWidth) / 2  //Left
            val y =  25 //(bitmap.height - textHeight) / 2  //Top

            //Setando a altura do bitmap baseado na altura do multiline text do staticLayout
            bitmap.height = textHeight

            Log.i("TESTE", "TextWidth: $textWidth, TextHeight: $textHeight")

            // draw text to the Canvas center
            canvas.save()
            canvas.translate(x.toFloat(), y.toFloat())
            textLayout.draw(canvas)
            canvas.restore()

            return bitmap
        }

    }
}