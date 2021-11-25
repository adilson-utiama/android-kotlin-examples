package com.asuprojects.kotlincustomcomponents.prototypes.blurimage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.widget.AppCompatImageView

class ViewSwitchTransitionUtil {

    companion object {

        fun startSwitchBackgroundAnim(
            context: Context,
            imageView: AppCompatImageView,
            bitmap: Bitmap
        ){
            val oldDrawable = imageView.drawable
            var oldBitmapDrawable: Drawable? = null
            var oldTransitionDrawable: TransitionDrawable? = null

            when (oldDrawable) {
                is TransitionDrawable -> {
                    oldTransitionDrawable = oldDrawable as TransitionDrawable
                    oldBitmapDrawable = oldTransitionDrawable.findDrawableByLayerId(
                        oldTransitionDrawable.getId(
                            1
                        )
                    )
                }
                is BitmapDrawable -> {
                    oldBitmapDrawable = oldDrawable
                }
                else -> {
                    oldBitmapDrawable = ColorDrawable(Color.GRAY)
                }
            }

            if(oldTransitionDrawable == null){
                oldTransitionDrawable = TransitionDrawable(
                    arrayOf(
                        oldBitmapDrawable, BitmapDrawable(
                            context.resources,
                            bitmap
                        )
                    )
                )
                oldTransitionDrawable.setId(0, 0)
                oldTransitionDrawable.setId(1, 1)
                oldTransitionDrawable.isCrossFadeEnabled = true

                imageView.setImageDrawable(oldTransitionDrawable)
            }else{
                oldTransitionDrawable.setDrawableByLayerId(
                    oldTransitionDrawable.getId(0),
                    oldBitmapDrawable
                )
                oldTransitionDrawable.setDrawableByLayerId(
                    oldTransitionDrawable.getId(1),
                    BitmapDrawable(context.resources, bitmap)
                )
            }

            oldTransitionDrawable.startTransition(1000)
        }
    }
}