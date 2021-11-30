package com.asuprojects.kotlincustomcomponents.fragments.layouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.FragmentExpandViewTMBinding
import com.google.android.material.animation.AnimationUtils

class ExpandViewTMFragment : Fragment() {

    private var _bind: FragmentExpandViewTMBinding? = null
    private val bind get() = _bind!!

    private var show = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentExpandViewTMBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Utilizando TransitionManager
        bind.clickableView.setOnClickListener {

            val autoTransition = AutoTransition()
            autoTransition.duration = 2000L

            if(bind.expandableView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(bind.expandableView,
                    autoTransition
                )
                bind.expandableView.visibility = View.VISIBLE
            }else{
                TransitionManager.beginDelayedTransition(bind.expandableView,
                    autoTransition
                )
                bind.expandableView.visibility = View.GONE
            }
        }

        bind.clickableView2.setOnClickListener {
            show = !show
            if(show){
                slideDown(bind.expandableView2)
                bind.expandableView2.visibility = VISIBLE
            }else{
                slideUp(bind.expandableView2)
                bind.expandableView2.visibility = GONE
            }

        }

    }


    fun slideDown(view: View) {
        val slideDownAnim = android.view.animation.AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_down)
        if(slideDownAnim != null){
            slideDownAnim.reset()
            if(view != null){
                view.clearAnimation()
                view.startAnimation(slideDownAnim)
            }
        }
    }

    fun slideUp(view: View) {
        val slideUpAnim = android.view.animation.AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_up)
        if(slideUpAnim != null){
            slideUpAnim.reset()
            if(view != null){
                view.clearAnimation()
                view.startAnimation(slideUpAnim)
            }
        }
    }

}