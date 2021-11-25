package com.asuprojects.kotlincustomcomponents.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Environment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream


interface OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
    fun onItemLongClicked(position: Int, view: View)
}

fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            view.setOnClickListener(null)
            view.setOnLongClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
            view.setOnLongClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemLongClicked(holder.adapterPosition, view)
                return@setOnLongClickListener true
             }
        }
    })
}

fun <K : RecyclerView.ViewHolder> RecyclerView.getWholeRecyclerView(callback: (path: String) -> Unit) {
    //async {
        val file = File(Environment.getExternalStorageDirectory(), "screenshot-" + System.currentTimeMillis() + ".jpg")
        file.createNewFile()
        try {
            val itemsCount = adapter!!.itemCount
            val holder = adapter!!.createViewHolder(this@getWholeRecyclerView, 0)
            (adapter as RecyclerView.Adapter<K>).onBindViewHolder(holder as K, 0)
            holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            holder.itemView.layout(0, 0, holder.itemView.measuredWidth, holder.itemView.measuredHeight)
            val bigBitmap = Bitmap.createBitmap(measuredWidth, holder.itemView.measuredHeight * itemsCount, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            val paint = Paint()
            var iHeight = 0
            holder.itemView.isDrawingCacheEnabled = true
            holder.itemView.buildDrawingCache()
            bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight.toFloat(), paint)
            holder.itemView.isDrawingCacheEnabled = false
            holder.itemView.destroyDrawingCache()
            iHeight += holder.itemView.measuredHeight
            for (i in 1..itemsCount - 1) {
                (adapter as RecyclerView.Adapter<K>).onBindViewHolder(holder, i)
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight.toFloat(), paint)
                iHeight += holder.itemView.measuredHeight
                holder.itemView.isDrawingCacheEnabled = false
                holder.itemView.destroyDrawingCache()
            }

            file.deleteOnExit()
            FileOutputStream(file).use { stream ->
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            file.delete()
            callback("")
            //return@async
        }
        callback(file.absolutePath)
    //}
}


