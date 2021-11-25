package com.asuprojects.kotlincustomcomponents.fragments.lists.utils

import android.content.Context
import android.graphics.*
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.fragments.lists.adapters.SwipeDragDropItemAdapter
import com.google.android.material.snackbar.Snackbar



class SwipeAndDragDropHelper(
    val context: Context,
    val list: MutableList<*>,
    val adapter: OnSwipeDragDropAction,
    val deleteIconResource: Int,
    val editIconresource: Int
) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)  {

    private val paint = Paint()

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        if (direction == ItemTouchHelper.LEFT) {
            val deletedModel = list[position]
            adapter.removeItem(position)
//                        list.removeAt(position)
//                        adapter.notifyItemRemoved(position)
//                        adapter.notifyItemRangeChanged(position, list.size)
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
//                            list.add(deletedModel)
//                            adapter.notifyItemInserted(position)

            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        } else {
            val deletedModel = list[position]
            adapter.removeItem(position)
//                        list.removeAt(position)
//                        adapter.notifyItemRemoved(position)
//                        adapter.notifyItemRangeChanged(position, list.size)
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
//                            list.add(deletedModel)
//                            adapter.notifyItemInserted(position)
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
                icon = getBitmapFromVectorDrawable(context, deleteIconResource)
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
                icon = getBitmapFromVectorDrawable(context, editIconresource)
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

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
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

    interface OnSwipeDragDropAction {
        fun removeItem(position: Int)
        fun editItem(position: Int)
        fun restoreItem(obj: Any, position: Int)
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(itemViewHolder: SwipeDragDropItemAdapter.ItemViewHolder)
        fun onRowClear(itemViewHolder: SwipeDragDropItemAdapter.ItemViewHolder)
    }

    interface StartDragListener{
        fun requestDrag(viewHolder: RecyclerView.ViewHolder)
    }
}

