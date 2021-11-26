package com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.asuprojects.kotlincustomcomponents.databinding.FragmentRecipeVerticalBinding
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical.NecessaryItemsVAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical.PrincipalVAdapter
import com.asuprojects.kotlincustomcomponents.fragments.lists.concatadapter.adapters.vertical.ProceduresVAdapter
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil

class RecipeVerticalFragment : Fragment() {

    private var _binding: FragmentRecipeVerticalBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeVerticalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val principalAdapter = PrincipalVAdapter()
        principalAdapter.setListener(object: PrincipalVAdapter.OnClickListener{
            override fun onClickDetails(text: String) {
                ToastUtil.msg(requireActivity(), text)
            }

        })
        val necessaryItemsAdapter = NecessaryItemsVAdapter()
        necessaryItemsAdapter.setListener(object: NecessaryItemsVAdapter.OnClickListener{
            override fun onClickDetails(text: String) {
                ToastUtil.msg(requireActivity(), text)
            }

        })
        val proceduresAdapter = ProceduresVAdapter()


        val listOfAdapters = listOf(principalAdapter, necessaryItemsAdapter, proceduresAdapter)

        val concatAdapter = ConcatAdapter(listOfAdapters)

        binding.recyclerRecipeVertical.adapter = concatAdapter

    }

}