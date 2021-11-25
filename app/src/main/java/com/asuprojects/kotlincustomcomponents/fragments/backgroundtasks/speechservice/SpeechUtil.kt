package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice

import android.content.Context
import android.content.Intent
import android.os.Build
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_MEANING
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_WORD

class SpeechUtil {

    companion object {

        fun speakMsg(context: Context, title: String, content: String){

            val intent = Intent(context, SpeechService::class.java)
            intent.putExtra(EXTRA_WORD, title)
            intent.putExtra(EXTRA_MEANING, content)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }

        }

        fun speakSimpleMsg(context: Context, content: String){

            val intent = Intent(context, SpeechService::class.java)
            intent.putExtra(EXTRA_MEANING, content)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }

        }


    }
}