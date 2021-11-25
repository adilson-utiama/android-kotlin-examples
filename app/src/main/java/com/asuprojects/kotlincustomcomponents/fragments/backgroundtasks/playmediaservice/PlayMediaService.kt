package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.playmediaservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

class PlayMediaService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //gettings systems default ringtone
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

        //setting loop play to true
        //this will make the ringtone continuously playing
        mediaPlayer.isLooping = true

        //starting player
        mediaPlayer.start()

        //We have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //stopping the player when service is destroyed
        mediaPlayer.stop()
    }
}