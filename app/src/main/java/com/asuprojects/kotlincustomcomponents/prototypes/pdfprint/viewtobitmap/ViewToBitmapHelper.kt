package com.asuprojects.kotlincustomcomponents.prototypes.pdfprint.viewtobitmap

import android.app.Activity
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.pranavpandey.android.dynamic.utils.DynamicUnitUtils


class ViewToBitmapHelper {

    companion object {

        fun getBitmapFromView1(view: View): Bitmap? {

            //Define a bitmap with the same size as the view
            val returnedBitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

            //Bind a canvas to it
            val canvas = Canvas(returnedBitmap)

            //Get the view's background
            val drawable = view.background

            if (drawable != null) {
                //has background drawable, then draw it on the canvas
                drawable.draw(canvas)
            } else {
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE)
            }

            // draw the view on the canvas
            view.draw(canvas)

            return returnedBitmap
        }

        fun getBitmapFromView2(view: View): Bitmap? {
            val bitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

        fun getBitmapFromView3(view: View, defaultColor: Int): Bitmap? {
            val bitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(defaultColor)
            view.draw(canvas)
            return bitmap
        }

        fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
            activity.window?.let { window ->
                val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                val locationOfViewInWindow = IntArray(2)
                view.getLocationInWindow(locationOfViewInWindow)
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        PixelCopy.request(window,
                            Rect(
                                locationOfViewInWindow[0],
                                locationOfViewInWindow[1],
                                locationOfViewInWindow[0] + view.width,
                                locationOfViewInWindow[1] + view.height
                            ), bitmap, { copyResult ->
                                if (copyResult == PixelCopy.SUCCESS) {
                                    callback(bitmap)
                                }
                                // possible to handle other result codes ...
                            }, Handler()
                        )
                    }
                } catch (e: IllegalArgumentException) {
                    // PixelCopy may throw IllegalArgumentException, make sure to handle it
                    e.printStackTrace()
                }
            }
        }

        // Usando Biblioteca DinamicUtils DEPRECATED
        /**
         * Creates a bitmap from the supplied view.
         *
         * @param view The view to get the bitmap.
         * @param width The width for the bitmap.
         * @param height The height for the bitmap.
         *
         * @return The bitmap from the supplied drawable.
         */
        fun createBitmapFromViewOld(view: View, width: Int, height: Int): Bitmap {
            if (width > 0 && height > 0) {
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        DynamicUnitUtils
                            .convertDpToPixels(width.toFloat()), View.MeasureSpec.EXACTLY
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        DynamicUnitUtils
                            .convertDpToPixels(height.toFloat()), View.MeasureSpec.EXACTLY
                    )
                )
            }
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            val bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            return bitmap
        }

        // Usando Biblioteca DinamicUtils
        /**
         * Creates a bitmap from the supplied view.
         *
         * @param view The view to get the bitmap.
         * @param width The width for the bitmap.
         * @param height The height for the bitmap.
         *
         * @return The bitmap from the supplied drawable.
         */
        fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
            if (width > 0 && height > 0) {
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        DynamicUnitUtils
                            .convertDpToPixels(width.toFloat()), View.MeasureSpec.EXACTLY
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        DynamicUnitUtils
                            .convertDpToPixels(height.toFloat()), View.MeasureSpec.EXACTLY
                    )
                )
            }
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            val bitmap = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            val background = view.background
            background?.draw(canvas)
            view.draw(canvas)
            return bitmap
        }

        fun createBitmapFromView(view: View): Bitmap {
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            val bitmap = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            val background = view.background
            background?.draw(canvas)
            view.draw(canvas)
            return bitmap
        }

        // Take a screen at your device:
        // view.getDrawingCache() is deprecated in Android API 28
        fun takeScreenshootDevice(view: View): Bitmap {
            val bitmap: Bitmap?
            val rootView = view.rootView
            rootView.isDrawingCacheEnabled = true
            bitmap = Bitmap.createBitmap(rootView.drawingCache)
            rootView.isDrawingCacheEnabled = false
            return bitmap
        }

        // If you're having ScrollView as root view then:
        // view.getDrawingCache() is deprecated in Android API 28
        fun takeScreenshootDeviceScrollView(activity: Activity, rootViewId: Int): Bitmap? {
            val inflater = LayoutInflater.from(activity)
            val root = inflater.inflate(
                R.layout.activity_main,
                null
            ) //RelativeLayout is root view of my UI(xml) file.
            root.isDrawingCacheEnabled = true
            val screen = getBitmapFromView1(activity.window.findViewById(rootViewId))
            return screen
        }


        fun getRecyclerViewScreenshot(view: RecyclerView): Bitmap? {
            val size = view.adapter!!.itemCount
            val holder = view.adapter!!.createViewHolder(view, 0)
            view.adapter!!.onBindViewHolder(holder, 0)
            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(
                    view.width,
                    View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
            )
            holder.itemView.layout(
                0,
                0,
                holder.itemView.measuredWidth,
                holder.itemView.measuredHeight
            )
            val bigBitmap = Bitmap.createBitmap(
                view.measuredWidth, holder.itemView.measuredHeight * size,
                Bitmap.Config.ARGB_8888
            )
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            val paint = Paint()
            var iHeight = 0
            holder.itemView.isDrawingCacheEnabled = true
            holder.itemView.buildDrawingCache()
            bigCanvas.drawBitmap(bigBitmap, 0f, iHeight.toFloat(), paint)
            holder.itemView.isDrawingCacheEnabled = false
            holder.itemView.destroyDrawingCache()
            iHeight += holder.itemView.measuredHeight
            try {
                for (i in 1 until size) {
                    view.adapter!!.onBindViewHolder(holder, i)
                    holder.itemView.isDrawingCacheEnabled = true
                    holder.itemView.buildDrawingCache()
                    bigCanvas.drawBitmap(bigBitmap, 0f, iHeight.toFloat(), paint)
                    iHeight += holder.itemView.measuredHeight
                    holder.itemView.isDrawingCacheEnabled = false
                    holder.itemView.destroyDrawingCache()
                }
            } catch (e: Exception) {
            }
            return bigBitmap
        }


    }


}