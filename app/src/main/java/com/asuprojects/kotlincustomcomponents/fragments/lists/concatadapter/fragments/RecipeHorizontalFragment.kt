package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.asuprojects.kotlincustomcomponents.databinding.FragmentRecipeHorizontalBinding
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.horizontal.NecessaryItemsHAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.horizontal.PrincipalHAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.horizontal.ProceduresHAdapter
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil

class RecipeHorizontalFragment : Fragment() {

    private var _binding: FragmentRecipeHorizontalBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeHorizontalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerRecipeHorizontal.layoutManager = layoutManager
        val pageSnap = PagerSnapHelper()
        pageSnap.attachToRecyclerView(binding.recyclerRecipeHorizontal)


        val principalAdapter = PrincipalHAdapter()
        principalAdapter.setOnClickListener(object: PrincipalHAdapter.OnClickListener{
            override fun onClickDetails(text: String) {
                ToastUtil.msg(requireActivity(), text)
            }

        })
        val necessaryItemsAdapter = NecessaryItemsHAdapter()
        necessaryItemsAdapter.setListener(object: NecessaryItemsHAdapter.OnClickListener{
            override fun onClickDetails(text: String) {
                ToastUtil.msg(requireActivity(), text)
            }

        })
        val proceduresAdapter = ProceduresHAdapter()


        val listOfAdapters = listOf(principalAdapter, necessaryItemsAdapter, proceduresAdapter)

        val concatAdapter = ConcatAdapter(listOfAdapters)

        binding.recyclerRecipeHorizontal.adapter = concatAdapter


    }

}