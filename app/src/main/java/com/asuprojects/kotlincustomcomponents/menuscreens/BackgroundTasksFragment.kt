package com.asuprojects.kotlincustomcomponents.menuscreens


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager.AlarmActivity
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.playmediaservice.PlayMediaActivity
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.TextSpeechActivity
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.workmanager.WorkManagerExActivity
import kotlinx.android.synthetic.main.fragment_background_tasks.*

class BackgroundTasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_mediaplayer_service.setOnClickListener {
            startActivity(Intent(context, PlayMediaActivity::class.java))
        }

        btn_alarmmanager_service.setOnClickListener {
            startActivity(Intent(context, AlarmActivity::class.java))
        }

        btn_speech_service.setOnClickListener {
            startActivity(Intent(context, TextSpeechActivity::class.java))
        }

        btn_workmanager.setOnClickListener {
            startActivity(Intent(context, WorkManagerExActivity::class.java))
        }
    }


}
