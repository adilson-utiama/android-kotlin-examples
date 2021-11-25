package com.asuprojects.kotlincustomcomponents.fragments.media.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asuprojects.kotlincustomcomponents.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_audio_record_controller.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

interface OnRecordAudio {
    fun onFinishRecordAudio(filePath: String)
}

class CustomAudioRecorder(private var defaultFilesDir: String? = "") : BottomSheetDialogFragment() {

    private var fileName: String = ""
    private var player: MediaPlayer? = null
    private var recorder: MediaRecorder? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var isRecording: Boolean = false
    private var isPlaying: Boolean = false
    private var isPause: Boolean = false
    private var stopRecord: Boolean = false
    private var stopPlay: Boolean = false

    private var atualVolume: Int = 0
    private var maxVolume: Int = 0

    private lateinit var viewModel: AudioRecorder
    private lateinit var audioManager: AudioManager


    private var onRecordAudio: OnRecordAudio? = null


    fun setOnRecordAudio(listener: OnRecordAudio){
        this.onRecordAudio = listener
    }

    fun setDefaultFilesDir(filesDir: String){
        this.defaultFilesDir = filesDir
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(defaultFilesDir == ""){
            defaultFilesDir = context?.externalCacheDir?.absolutePath.toString()
        }


        viewModel = ViewModelProvider(this).get(AudioRecorder::class.java)
        audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        atualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_audio_record_controller, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        recorder?.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hidePlayControllers()
        hidePanelAudioTrack()
        btn_audio_record_stop.visibility = GONE
        btn_audio_recorded_done.visibility = GONE

        text_audio_record_time
        progressbar_audio_record
        text_audio_begin_record
        text_audio_end_record

        progressbar_audio_record.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i("TESTE", "onStopTrackingTouch... ${seekBar!!.progress}")
                if(player != null){
                    player!!.seekTo(seekBar!!.progress)
                }
            }

        })

        btn_audio_record_play.setOnClickListener {
            if(!isRecording){
                startPlaying()
            }
        }

        btn_audio_record_pause.setOnClickListener {}

        btn_audio_record_stop.setOnClickListener {
            if(isRecording){
                stopRecording()
            }else{
                stopPlaying()
            }
        }

        btn_audio_record.setOnClickListener {
            isRecording = true

            stopRecord = false
            startRecording()

            btn_audio_record_stop.visibility = VISIBLE
            btn_audio_record.visibility = GONE
            startCounter(text_audio_record_time)

            hidePanelAudioTrack()
            hidePlayControllers()
        }

        btn_close_panel_record.setOnClickListener {
            if(isRecording) stopRecording()
            if(isPlaying) stopPlaying()
            dismiss()
        }

        btn_audio_recorded_done.setOnClickListener {
            onRecordAudio?.onFinishRecordAudio(fileName)
            dismiss()
        }
    }

    private fun stopPlaying() {
        Log.i("TESTE", "Stop Playing...")

        player?.release()
        player = null

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, atualVolume, 0)
        isPlaying = false
        stopPlay = true
        progressbar_audio_record.progress = 0
        text_audio_begin_record.text = "00:00"

    }

    private fun startPlaying() {

        if(fileName.isNotEmpty()){

            Log.i("TESTE", "Start Playing...")

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (maxVolume - 2), 0)

            isPlaying = true

            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()
                    progressbar_audio_record.max = duration
                    text_audio_end_record.text = formatingTime(duration)

                } catch (e: IOException) {
                    Log.i("TESTE", "prepare() Failed...")
                }
            }

            viewModel.viewModelScope.launch {
                val total = player?.duration
                var currentPosition = player?.currentPosition

                while(isPlaying && currentPosition!! < total!!){
                    try{
                        delay(100)
                        currentPosition = player!!.currentPosition
                        text_audio_begin_record.text = formatingTime(currentPosition!!)
                        progressbar_audio_record.progress = currentPosition!!

                    }catch (e: Exception){

                    }

                }
            }

            player?.setOnCompletionListener {
                Log.i("TESTE", "OnCompletionListener: IsPlaying: ${it.isPlaying}")

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, atualVolume, 0)
                isPlaying = false
                //progressbar_audio.progress = 0
                stopPlay = true

            }
        }

    }

    fun formatingTime(duration: Int): String {
        var duracao: Any = 0
        var texto = ""

        if(duration > 100000){
            duracao = (duration / 100000).toFloat()
            val array = duracao.toString().split(".")
            texto = "${array[0]}:${array[1]}"
        }else{

            val value = (duration.toDouble() / 1000)
            if(value > value.toInt()){
                duracao = value.toInt() + 1
            }else{
                duracao = value.toInt()
            }
            Log.i("TESTE", "Duration: $duration, duracao: ${(duration.toDouble() / 1000)}")

            if(duracao > 9){
                texto = "00:$duracao"
            }else{
                texto = "00:0$duracao"
            }

        }
        return texto
    }

    private fun startRecording() {

        btn_audio_recorded_done.visibility = GONE

        Log.i("TESTE", "Start Recording...")

        val timemillis = Calendar.getInstance().timeInMillis
        // Record to the external cache directory for visibility
        fileName = "$defaultFilesDir/audio_$timemillis.mp4"
        Log.i("TESTE", "FileName: $fileName")

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {

            }
            start()
        }

        btn_audio_record.isEnabled = false

    }

    private fun stopRecording() {
        Log.i("TESTE", "Stop Recording...")

        isRecording = false
        stopRecord = true

        btn_audio_record_stop.visibility = VISIBLE
        btn_audio_record.visibility = VISIBLE
        btn_audio_record.isEnabled = true

        btn_audio_recorded_done.visibility = VISIBLE

        showPanelAudioTrack()

        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        showPlayControllers()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) dismiss()
    }


    private fun hidePlayControllers() {
        btn_audio_record_play.visibility = GONE
        btn_audio_record_pause.visibility = GONE
    }

    private fun showPlayControllers() {
        btn_audio_record_play.visibility = VISIBLE
        btn_audio_record_pause.visibility = VISIBLE
    }

    private fun hidePanelAudioTrack() {
        panel_audio_track.visibility = GONE
    }

    private fun showPanelAudioTrack() {
        panel_audio_track.visibility = VISIBLE
    }

    private fun startCounter(textView: AppCompatTextView) {
        var counter = "00:00"

        var seconds = 0
        var minutes = 0

        viewModel.viewModelScope.launch {
            textView.text = counter
            if(isRecording) {
                while(!stopRecord){
                    seconds++
                    if(seconds >= 60){
                        minutes++
                        seconds = 0
                    }

                    var incluirZeroSegundos = "0"
                    var incluirZeroMinutos = "0"
                    if(seconds > 9) incluirZeroSegundos = ""
                    if(minutes > 9) incluirZeroMinutos = ""

                    counter = "$incluirZeroMinutos$minutes:$incluirZeroSegundos$seconds"

                    textView.text = counter

                    delay(1000)
                }
            }

        }
    }

    class AudioRecorder : ViewModel()
}