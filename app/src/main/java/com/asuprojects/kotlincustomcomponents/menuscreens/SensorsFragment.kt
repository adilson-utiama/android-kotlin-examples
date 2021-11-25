package com.asuprojects.kotlincustomcomponents.menuscreens


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.MainItem
import com.asuprojects.kotlincustomcomponents.MainItemAdapter
import com.asuprojects.kotlincustomcomponents.OnClickItem

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.BottomSheetsFragment
import com.asuprojects.kotlincustomcomponents.fragments.buttons.ButtonsFragment
import com.asuprojects.kotlincustomcomponents.fragments.dialogs.AlertDialogsFragment
import com.asuprojects.kotlincustomcomponents.fragments.lists.ListsExamplesFragment
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsFragment
import com.asuprojects.kotlincustomcomponents.fragments.pickers.PickersFragment
import com.asuprojects.kotlincustomcomponents.fragments.runtimepermission.RuntimePermissionFragment
import com.asuprojects.kotlincustomcomponents.fragments.sensors.ShakeDetectionFragment
import com.asuprojects.kotlincustomcomponents.fragments.storage.StorageFragment
import kotlinx.android.synthetic.main.fragment_interface_layouts.*
import kotlinx.android.synthetic.main.fragment_sensors.*

class SensorsFragment : Fragment() {

    private var mainItemList = mutableListOf<MainItem>()
    private lateinit var adapter: MainItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainItemList = getList()
        adapter =
            MainItemAdapter(mainItemList)
        adapter.setOnClickItem(object :
            OnClickItem {
            override fun onClickMainItem(view: View, position: Int) {
                val mainItem = mainItemList[position]
                val tx = requireActivity().supportFragmentManager.beginTransaction()
                tx.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                tx.addToBackStack(mainItem.descricao)
                tx.replace(R.id.framelayout_main_window, mainItem.fragment)
                tx.commit()
            }
        })
        recycler_sensors.layoutManager = LinearLayoutManager(requireContext())
        recycler_sensors.adapter = adapter
    }

    private fun getList(): MutableList<MainItem> {
        return mutableListOf(
            MainItem(
                "Shake Detection",
                ShakeDetectionFragment()
            )
        )
    }
}
