package com.asuprojects.kotlincustomcomponents.menuscreens


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.architecture.room.RoomDatabaseActivity
import kotlinx.android.synthetic.main.fragment_android_architecture.*


class AndroidArchitectureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_android_architecture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_room_database.setOnClickListener {
            startActivity(Intent(requireActivity(), RoomDatabaseActivity::class.java))
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

}
