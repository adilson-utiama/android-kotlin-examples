package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.fragment_weapon_detail.*


class WeaponDetailFragment : Fragment() {

    companion object {
        val WEAPON_ITEM_ARG = "WeaponItemArg"
    }

    private var weapon: Weapon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.containsKey(WEAPON_ITEM_ARG)){
                weapon = it.getParcelable(WEAPON_ITEM_ARG)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weapon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weapon?.apply {
            weapon_detail_image.setImageResource(this.resourdId)
            weapon_detail_model.text = this.model
        }

    }

}