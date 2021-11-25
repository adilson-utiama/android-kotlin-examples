package com.asuprojects.kotlincustomcomponents.fragments.lists


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.lists.dragdrop.RecyclerSwipeDragDropActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandable.ProtoExpandableRecyclerActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandablelistview.ExpandableListViewActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.gridlayoutmanager.GridLayoutManagerActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.menu.RecyclerItemMenuActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.RecyclerInsideActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.searchview.RecyclerSearchViewActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.swipe.RecyclerSwipeActionsActivity
import kotlinx.android.synthetic.main.fragment_lists_examples.*

class ListsExamplesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_expandable_list_view.setOnClickListener {
            startActivity(Intent(requireActivity(), ExpandableListViewActivity::class.java))
        }

        btn_list_with_swipe.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSwipeActionsActivity::class.java))
        }

        btn_list_swipe_drag_drop.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSwipeDragDropActivity::class.java))
        }

        btn_list_item_menu.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerItemMenuActivity::class.java))
        }

        btn_proto_expandable_recycler.setOnClickListener {
            startActivity(Intent(requireActivity(), ProtoExpandableRecyclerActivity::class.java))
        }

        btn_recycler_inside_recycler.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerInsideActivity::class.java))
        }

        btn_recycler_searchview.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSearchViewActivity::class.java))
        }

        btn_recycler_gridlayout_manager.setOnClickListener {
            startActivity(Intent(requireActivity(), GridLayoutManagerActivity::class.java))
        }
    }

}
