package com.asuprojects.kotlincustomcomponents.helpers

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

interface OnSwipeListener{
    fun onSwipeUp()
    fun onSwipeDown()
    fun onSwipeLeft()
    fun onSwipeRight()
}

class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {

    val SWIPE_THRESHOLD = 100
    val SWIPE_VELOCITY_THRESHOLD = 100

    private var onSwipeListener: OnSwipeListener? = null

    fun setOnSwipeListener(listener: OnSwipeListener){
        this.onSwipeListener = listener
    }

    override fun onDown(event: MotionEvent): Boolean {
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        var result = false
        try{
            val diffY = event2.y - event1.y
            val diffX = event2.x - event1.x

            //Detecta Movimentos Horizontais
            if(abs(diffX) > abs(diffY)) {
                if(abs(diffX) > SWIPE_THRESHOLD
                    && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if(diffX > 0) {
                        swipeRight()
                    } else {
                        swipeLeft()
                    }
                }
            } else {
                // onTouch(e)
            }

            if(abs(diffY) > abs(diffX)) {
                if(abs(diffY) > SWIPE_THRESHOLD
                    && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if(diffY < 0) {
                        swipeUp()
                    } else {
                        swipeDown()
                    }
                }
            } else {
                //onTouch(e)
            }
        } catch(e: Exception) {

        }
        //Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return result
    }

    private fun swipeDown() {
        Log.i("TESTE", "Deslocando para Baixo....")
        this.onSwipeListener?.onSwipeDown()
    }

    private fun swipeUp() {
        Log.i("TESTE", "Deslocando para Cima...")
        this.onSwipeListener?.onSwipeUp()
    }

    private fun swipeRight() {
        Log.i("TESTE", "Deslocando para Direita...")
        this.onSwipeListener?.onSwipeRight()
    }
    private fun swipeLeft() {
        Log.i("TESTE", "Deslocando para Esquerda...")
        this.onSwipeListener?.onSwipeLeft()
    }

}