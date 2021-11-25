package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.speech.tts.TextToSpeech
import com.asuprojects.kotlincustomcomponents.fragments.notifications.NotificationsUtil
import java.util.*

class SpeechService : Service(), TextToSpeech.OnInitListener {

    private val SPEECH_SERVICE_ID = 300

    companion object {
        val EXTRA_WORD = "word"
        val EXTRA_MEANING = "meaning"
        val EXTRA_SPEECH_RATE = "extra_speech_rate"
        val EXTRA_SPEECH_SPEED = "extra_speech_speed"
    }

    private var word = ""
    private var meaning = ""

    private var tts: TextToSpeech? = null
    private var isInit: Boolean = false
    private var handler: Handler? = null
    private var audioManager: AudioManager? = null

    private var voicePitchrate = 3
    private var voiceSpeedRate = 3

    private var deviceMaxVolume: Int? = 0
    private var deviceAtualVolume: Int? = 0

    override fun onCreate() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForeground(SPEECH_SERVICE_ID, NotificationsUtil.simpleNotification(applicationContext,
                "Speech Service working..."))
        }

        tts = TextToSpeech(applicationContext, this)
        audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager?

        deviceMaxVolume = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        deviceAtualVolume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)

        try{
            audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC,
                (deviceMaxVolume!!.div(2).plus(3)), 0)
        }catch(e: Exception){
            audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, deviceAtualVolume!!, 0)
        }

        handler = Handler()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        handler?.removeCallbacksAndMessages(null)

        word = intent?.getStringExtra(EXTRA_WORD) as String
        meaning = intent.getStringExtra(EXTRA_MEANING) as String
        voicePitchrate = intent.getIntExtra(EXTRA_SPEECH_RATE, 3)
        voiceSpeedRate = intent.getIntExtra(EXTRA_SPEECH_SPEED, 3)
        if(isInit){
            speak()
        }

        val palavras = word + meaning

        val delay: Long = (palavras.length * 200).toLong()

        handler?.postDelayed(this::stopSelf, delay)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        tts?.apply {
            tts?.stop()
            tts?.shutdown()
            audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, deviceAtualVolume!!, 0)
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.getDefault())
            if(result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED){
                speak()
                isInit = true
            }
        }
    }

    private fun speak(){
        tts?.apply {

            tts?.setSpeechRate(voiceSpeedRate * 0.3F)
            tts?.setPitch(voicePitchrate * 0.35F)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null, word)
                tts?.speak(meaning, TextToSpeech.QUEUE_ADD, null, meaning)
            }else{
                tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null)
                tts?.speak(meaning, TextToSpeech.QUEUE_ADD, null)
            }

        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}