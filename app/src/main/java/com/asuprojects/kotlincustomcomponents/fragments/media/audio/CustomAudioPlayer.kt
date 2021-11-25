package com.asuprojects.kotlincustomcomponents.fragments.media.audio

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asuprojects.kotlincustomcomponents.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_audio_player_controller.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class CustomAudioPlayer(audioFile: File) : BottomSheetDialogFragment() {

    private lateinit var viewModel: AudioPlayerViewModel
    private var fileName: String = ""
    private var player: MediaPlayer? = null
    private var isPlaying: Boolean = false
    private var isPause: Boolean = false
    private var stopPlay: Boolean = false

    private var atualVolume: Int = 0
    private var maxVolume: Int = 0
    private lateinit var audioManager: AudioManager

    init {
        if (audioFile.exists()) {
            fileName = audioFile.absolutePath
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_audio_player_controller, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AudioPlayerViewModel::class.java)
        audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        atualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (maxVolume - 2), 0)

        isCancelable = false


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                //start()
                progressbar_audio_player.max = duration
                text_audio_end_player.text = formatingTime(duration)

            } catch (e: IOException) {
                Log.i("TESTE", "prepare() Failed...")
            }
        }

        progressbar_audio_player.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i("TESTE", "onStopTrackingTouch... ${seekBar!!.progress}")
                if (player != null) {
                    player!!.seekTo(seekBar!!.progress)
                }
            }

        })

        val split = fileName.split("/")
        val name = split[split.size - 1]
        text_audio_player_file_name.text = name

        text_audio_begin_player
        text_audio_end_player

        btn_audio_player_play.setOnClickListener {
            //if(!isPause){
            isPlaying = true
            //isPause = false

            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()
                    progressbar_audio_player.max = duration
                    text_audio_end_player.text = formatingTime(duration)

                } catch (e: IOException) {
                    Log.i("TESTE", "prepare() Failed...")
                }
            }

            viewModel.viewModelScope.launch {
                val total = player?.duration
                var currentPosition = player?.currentPosition

                while (isPlaying && currentPosition!! < total!!) {
                    try {
                        //if(!isPause) {
                        delay(100)
                        currentPosition = player!!.currentPosition
                        text_audio_begin_player.text = formatingTime(currentPosition!!)
                        progressbar_audio_player.progress = currentPosition!!
                        //}

                    } catch (e: Exception) {

                    }

                }
            }

            player?.setOnCompletionListener {
                Log.i("TESTE", "OnCompletionListener: IsPlaying: ${it.isPlaying}")

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, atualVolume, 0)
                isPlaying = false
                //isPause = false
                //progressbar_audio.progress = 0
                stopPlay = true

                resetPlay()

            }
            //}

        }
        btn_audio_player_pause.setOnClickListener {
            //isPause = true
            //player?.pause()
        }
        btn_audio_player_stop.setOnClickListener {
            isPlaying = false
            stopPlay = true
            isPause = false
            player?.stop()
            player?.reset()
            progressbar_audio_player.progress = 0
            text_audio_begin_player.text = "00:00"
        }
        btn_close_panel_player.setOnClickListener {
            stopPlaying()
            dismiss()
        }

    }

    private fun resetPlay() {
        isPlaying = false
        stopPlay = true
        isPause = false
        player?.stop()
        player?.reset()
        progressbar_audio_player.progress = 0
        text_audio_begin_player.text = "00:00"
    }

    private fun stopPlaying() {
        isPlaying = false
        stopPlay = true
        player?.stop()
        player?.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    fun formatingTime(duration: Int): String {
        var duracao: Any = 0
        var texto = ""

        if (duration > 100000) {
            duracao = (duration / 100000).toFloat()
            val array = duracao.toString().split(".")
            texto = "${array[0]}:${array[1]}"
        } else {

            val value = (duration.toDouble() / 1000)
            if (value > value.toInt()) {
                duracao = value.toInt() + 1
            } else {
                duracao = value.toInt()
            }
            Log.i("TESTE", "Duration: $duration, duracao: ${(duration.toDouble() / 1000)}")

            if (duracao > 9) {
                texto = "00:$duracao"
            } else {
                texto = "00:0$duracao"
            }

        }
        return texto
    }

    class AudioPlayerViewModel : ViewModel()
}