package com.asuprojects.kotlincustomcomponents.fragments.lists


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.databinding.FragmentListsExamplesBinding
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.ConcatAdapterExampleActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.dragdrop.RecyclerSwipeDragDropActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandable.ProtoExpandableRecyclerActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.expandablelistview.ExpandableListViewActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.gridlayoutmanager.GridLayoutManagerActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.menu.RecyclerItemMenuActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.recyclerinside.RecyclerInsideActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.searchview.RecyclerSearchViewActivity
import com.asuprojects.kotlincustomcomponents.fragments.lists.swipe.RecyclerSwipeActionsActivity

class ListsExamplesFragment : Fragment() {

    private var _bind: FragmentListsExamplesBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentListsExamplesBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.btnConcatAdapter.setOnClickListener {
            startActivity(Intent(requireActivity(), ConcatAdapterExampleActivity::class.java))
        }

        bind.btnExpandableListView.setOnClickListener {
            startActivity(Intent(requireActivity(), ExpandableListViewActivity::class.java))
        }

        bind.btnListWithSwipe.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSwipeActionsActivity::class.java))
        }

        bind.btnListSwipeDragDrop.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSwipeDragDropActivity::class.java))
        }

        bind.btnListItemMenu.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerItemMenuActivity::class.java))
        }

        bind.btnProtoExpandableRecycler.setOnClickListener {
            startActivity(Intent(requireActivity(), ProtoExpandableRecyclerActivity::class.java))
        }

        bind.btnRecyclerInsideRecycler.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerInsideActivity::class.java))
        }

        bind.btnRecyclerSearchview.setOnClickListener {
            startActivity(Intent(requireActivity(), RecyclerSearchViewActivity::class.java))
        }

        bind.btnRecyclerGridlayoutManager.setOnClickListener {
            startActivity(Intent(requireActivity(), GridLayoutManagerActivity::class.java))
        }
    }

}
