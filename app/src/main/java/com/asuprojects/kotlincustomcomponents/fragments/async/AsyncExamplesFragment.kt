package com.asuprojects.kotlincustomcomponents.fragments.async

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.async.asynctask.AsyncTaskActivity
import kotlinx.android.synthetic.main.fragment_async_examples.*

class AsyncExamplesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_async_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_async_task_example.setOnClickListener {
            startActivity(Intent(requireActivity(), AsyncTaskActivity::class.java))
        }
    }

}