package com.asuprojects.kotlincustomcomponents.menuscreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity.SharedTransitionActivity
import com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment.SharedFragmentActivity
import kotlinx.android.synthetic.main.fragment_transitions.*

class TransitionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transitions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_activity_transitions.setOnClickListener {
            startActivity(Intent(requireActivity(), SharedTransitionActivity::class.java))
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        btn_fragment_transitions.setOnClickListener {
            startActivity(Intent(requireActivity(), SharedFragmentActivity::class.java))
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

}