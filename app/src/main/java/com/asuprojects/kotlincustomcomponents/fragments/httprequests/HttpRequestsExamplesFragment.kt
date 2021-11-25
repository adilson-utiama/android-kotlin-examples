package com.asuprojects.kotlincustomcomponents.fragments.httprequests

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.async.asynctask.AsyncTaskActivity
import com.asuprojects.kotlincustomcomponents.fragments.httprequests.downloadmanager.DownloadManagerActivity
import com.asuprojects.kotlincustomcomponents.fragments.httprequests.okhttp.OkHttpExampleActivity
import com.asuprojects.kotlincustomcomponents.fragments.httprequests.retrofit.RetrofitExampleActivity
import com.asuprojects.kotlincustomcomponents.fragments.httprequests.volley.VolleyRequestActivity
import kotlinx.android.synthetic.main.fragment_http_requests_examples.*

class HttpRequestsExamplesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_http_requests_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btn_async_request_with_volley.setOnClickListener {
            startActivity(Intent(requireActivity(), VolleyRequestActivity::class.java))
        }

        btn_async_request_with_okhttp.setOnClickListener {
            startActivity(Intent(requireActivity(), OkHttpExampleActivity::class.java))
        }

        btn_async_request_with_retrofit.setOnClickListener {
            startActivity(Intent(requireActivity(), RetrofitExampleActivity::class.java))
        }

        btn_download_manager.setOnClickListener {
            startActivity(Intent(requireActivity(), DownloadManagerActivity::class.java))
        }
    }

}