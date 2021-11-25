package com.asuprojects.kotlincustomcomponents.prototypes.blurimage.cardscalekotlin

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class SpeedRecyclerViewKotlin : RecyclerView {

    companion object {
        const val FLING_SCALE_DOWN_FACTOR: Float = 0.5f
        const val FLING_MAX_VELOCITY: Int = 8000
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val mVelocityX = solveVelocity(velocityX)
        val mVelocityY = solveVelocity(velocityY)
        return super.fling(mVelocityX, mVelocityY)
    }

    private fun solveVelocity(velocity: Int): Int {
        return if(velocity > 0){
            min(velocity, FLING_MAX_VELOCITY)
        }else{
            min(velocity, -FLING_MAX_VELOCITY)
        }
    }
}