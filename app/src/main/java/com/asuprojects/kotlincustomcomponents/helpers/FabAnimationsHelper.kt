package com.asuprojects.kotlincustomcomponents.helpers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.GONE

class FabAnimationsHelper {

    companion object {

        fun openFabMenu(view: View, rotate: Boolean): Boolean {
            view.animate()
                .rotation(if (rotate) 135f else 0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                    }
                })
                .duration = 200

            return rotate
        }

        fun closeFabMenu(view: View, rotate: Boolean): Boolean{
            view.animate()
                .rotation(if (rotate) 135f else 0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                    }
                })
                .duration = 200
            return rotate
        }

        fun showInFabMenuChild(view: View){
            view.visibility = View.VISIBLE
            view.alpha = 0f
            view.translationY = view.height.toFloat()
            view.animate()
                .setDuration(200)
                .translationY(0f)
                .setListener(object: AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }

        fun showOutFabMenuChild(view: View){
            view.visibility = View.VISIBLE
            view.alpha = 1f
            view.translationY = 0f
            view.animate()
                .setDuration(200)
                .translationY(view.height.toFloat())
                .setListener(object: AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        view.visibility = GONE
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(0f)
                .start()
        }

        fun initFabMenuChild(view: View){
            view.visibility = GONE
            view.translationY = view.height.toFloat()
            view.alpha = 0f
        }
    }
}