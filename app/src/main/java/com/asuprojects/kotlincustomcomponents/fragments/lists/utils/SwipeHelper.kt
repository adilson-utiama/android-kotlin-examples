package com.asuprojects.kotlincustomcomponents.fragments.lists.utils

import android.content.Context
import android.graphics.*
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.google.android.material.snackbar.Snackbar

interface OnSwipeAction {
    fun removeItem(position: Int)
    fun editItem(position: Int)
    fun restoreItem(obj: Any, position: Int)
}

class SwipeHelper(
    val context: Context,
    val list: MutableList<*>,
    val adapter: OnSwipeAction
) {

    private val paint = Paint()

    fun enableSwipe(recyclerView: RecyclerView) {
        val simpleItemTouchCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {
                        val deletedModel = list[position]
                        adapter.removeItem(position)
                        // showing snack bar with Undo option
                        val snackbar = Snackbar.make(
                            //activity!!.window.decorView.rootView,
                            viewHolder.itemView,
                            " removed from Recyclerview!",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.setAction("UNDO") {
                            // undo is selected, restore the deleted item
                            adapter.restoreItem(deletedModel!!, position)
                        }
                        snackbar.setActionTextColor(Color.YELLOW)
                        snackbar.show()
                    } else {
                        val deletedModel = list[position]
                        adapter.removeItem(position)
                        // showing snack bar with Undo option
                        val snackbar = Snackbar.make(
                            //activity!!.window.decorView.rootView,
                            viewHolder.itemView,
                            " removed from Recyclerview!",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.setAction("UNDO") {
                            // undo is selected, restore the deleted item
                            adapter.restoreItem(deletedModel!!, position)
                        }
                        snackbar.setActionTextColor(Color.YELLOW)
                        snackbar.show()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val icon: Bitmap
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        val itemView = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 3

                        if (dX > 0) {
                            paint.color = Color.parseColor("#D32F2F")
                            val background =
                                RectF(
                                    itemView.left.toFloat(),
                                    itemView.top.toFloat(),
                                    dX,
                                    itemView.bottom.toFloat()
                                )
                            c.drawRect(background, paint)
                            //icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_res)
                            icon = getBitmapFromVectorDrawable(context, R.drawable.ic_delete_white)
                            val iconDest = RectF(
                                itemView.left.toFloat() + width,
                                itemView.top.toFloat() + width,
                                itemView.left.toFloat() + 2 * width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, iconDest, paint)
                        } else {
                            paint.color = Color.parseColor("#3461eb")
                            val background = RectF(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, paint)
                            //icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_res)
                            icon = getBitmapFromVectorDrawable(context, R.drawable.ic_edit_white)
                            val iconDest = RectF(
                                itemView.right.toFloat() - 2 * width,
                                itemView.top.toFloat() + width,
                                itemView.right.toFloat() - width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, iconDest, paint)
                        }
                    }
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}