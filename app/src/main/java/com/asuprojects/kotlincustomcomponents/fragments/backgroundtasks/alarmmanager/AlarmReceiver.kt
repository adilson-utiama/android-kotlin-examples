package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechUtil

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        val EXTRA_ALARM_MSG_CONTENT = "EXTRA_ALARM_MSG_CONTENT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.apply {
            if(this.hasExtra(EXTRA_ALARM_MSG_CONTENT)){
                this.getStringExtra(EXTRA_ALARM_MSG_CONTENT).apply {
                    val msg = this
                    context?.apply {
                        SpeechUtil.speakMsg(this, "Alarme Disparado", msg)
                    }
                }
            }
        }

    }

}