package com.asuprojects.kotlincustomcomponents.prototypes.blurimage

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.appcompat.widget.AppCompatImageView

class BlurBitmapUtilsKotlin {

    companion object {

        private const val DEFAULT_BLUR_RADIUS = 20f
        private const val SCALED_WIDTH = 100
        private const val SCALED_HEIGHT = 100

        fun blur(appCompactImageView: AppCompatImageView, bitmap: Bitmap){
            blur(appCompactImageView, bitmap, DEFAULT_BLUR_RADIUS)
        }

        @JvmStatic
        fun blur(appCompactImageView: AppCompatImageView, bitmap: Bitmap, radius: Float){
            appCompactImageView.setImageBitmap(generateBlurBitmap(appCompactImageView.context, bitmap, radius))
        }


        fun getBlurBitmap(context: Context, bitmap: Bitmap): Bitmap{
            return generateBlurBitmap(context, bitmap, DEFAULT_BLUR_RADIUS)
        }

        private fun generateBlurBitmap(context: Context, bitmap: Bitmap, radius: Float): Bitmap{
            val inputBitmap = Bitmap.createScaledBitmap(bitmap, SCALED_WIDTH, SCALED_HEIGHT, false)

            val outputBitmap = Bitmap.createBitmap(inputBitmap)

            val renderScript = RenderScript.create(context)

            val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

            val tempInputBitmap = Allocation.createFromBitmap(renderScript, inputBitmap)
            val tempOutputBitmap = Allocation.createFromBitmap(renderScript, outputBitmap)

            blurScript.setRadius(radius)

            blurScript.setInput(tempInputBitmap)

            blurScript.forEach(tempOutputBitmap)

            tempOutputBitmap.copyTo(outputBitmap)

            return outputBitmap
        }
    }
}