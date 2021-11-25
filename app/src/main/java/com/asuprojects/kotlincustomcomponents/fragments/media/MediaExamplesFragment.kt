package com.asuprojects.kotlincustomcomponents.fragments.media


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.media.audio.AudioActivity
import com.asuprojects.kotlincustomcomponents.fragments.media.image.ImagesActivity
import com.asuprojects.kotlincustomcomponents.fragments.media.video.VideoActivity
import kotlinx.android.synthetic.main.fragment_media_examples.*

class MediaExamplesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_media_audio.setOnClickListener {
            startActivity(Intent(requireActivity(), AudioActivity::class.java))
        }

        btn_media_video.setOnClickListener {
            startActivity(Intent(requireActivity(), VideoActivity::class.java))
        }

        btn_media_photo.setOnClickListener {
            startActivity(Intent(requireActivity(), ImagesActivity::class.java))
        }
    }

}
