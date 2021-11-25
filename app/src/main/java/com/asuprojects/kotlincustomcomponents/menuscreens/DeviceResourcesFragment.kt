package com.asuprojects.kotlincustomcomponents.menuscreens


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.MainItem
import com.asuprojects.kotlincustomcomponents.MainItemAdapter
import com.asuprojects.kotlincustomcomponents.OnClickItem
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsFragment
import com.asuprojects.kotlincustomcomponents.fragments.runtimepermission.RuntimePermissionFragment
import com.asuprojects.kotlincustomcomponents.fragments.sensors.ShakeDetectionFragment
import com.asuprojects.kotlincustomcomponents.fragments.storage.StorageFragment
import kotlinx.android.synthetic.main.fragment_device_resources.*

class DeviceResourcesFragment : Fragment() {

    private var mainItemList = mutableListOf<MainItem>()
    private lateinit var adapter: MainItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_resources, container, false)
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
        recycler_device_resources.layoutManager = LinearLayoutManager(requireContext())
        recycler_device_resources.adapter = adapter
    }

    private fun getList(): MutableList<MainItem> {
        return mutableListOf(
            MainItem(
                "Runtime Permission",
                RuntimePermissionFragment()
            ),
            MainItem(
                "Storage Access",
                StorageFragment()
            )
        )
    }
}
