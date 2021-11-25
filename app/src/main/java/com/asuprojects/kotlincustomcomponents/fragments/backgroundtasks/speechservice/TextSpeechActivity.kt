package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_MEANING
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_SPEECH_RATE
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_SPEECH_SPEED
import com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.speechservice.SpeechService.Companion.EXTRA_WORD
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_text_speech.*

class TextSpeechActivity : AppCompatActivity() {

    private var audioManager: AudioManager? = null
    private var deviceMaxVolume: Int? = 0
    private var deviceAtualVolume: Int? = 0
    private var speechRate = 3
    private var speechSpeed = 3

    private var contentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_speech)

        supportActionBar?.apply {
            this.title = "Text to Speech Service"
            this.setDisplayHomeAsUpEnabled(true)
        }

        contentList = getContentList()

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        deviceMaxVolume = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        deviceAtualVolume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)

        speech_msg_title.setText("Título Padrão")
        speech_msg_content.setText(contentList[0])

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contentList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_selecao_msg_conteudo.adapter = adapter
        spinner_selecao_msg_conteudo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val content = contentList[position]
                    speech_msg_content.setText(content)
                }
            }

        speech_device_max_volume.text = deviceMaxVolume.toString()
        speech_device_atual_volume.text = deviceAtualVolume.toString()

        seekBar_speech_rate.max = 10
        seekBar_speech_speed.max = 10

        speech_rate_value.text = speechRate.toString()
        seekBar_speech_rate.progress = speechRate
        seekBar_speech_rate.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val value = seekBar!!.progress
                speech_rate_value.text = value.toString()
                speechRate = value
            }
        })

        speech_speed_value.text = speechSpeed.toString()
        seekBar_speech_speed.progress = speechSpeed
        seekBar_speech_speed.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val value = seekBar!!.progress
                speech_speed_value.text = value.toString()
                speechSpeed = value
            }
        })

        btn_speech_text.setOnClickListener {
            val title = speech_msg_title.text.toString()
            val content = speech_msg_content.text.toString()
            val intent = Intent(this@TextSpeechActivity, SpeechService::class.java)
            intent.putExtra(EXTRA_WORD, title)
            intent.putExtra(EXTRA_MEANING, content)
            intent.putExtra(EXTRA_SPEECH_RATE, speechRate)
            intent.putExtra(EXTRA_SPEECH_SPEED, speechSpeed)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }

    }

    private fun getContentList(): MutableList<String> {
        return mutableListOf(
            "Digite um Conteúdo ou selecione um acima...",
            "Esperamos que você participe do evento #Android11: apresentação de lançamento da versão Beta, apresentado por Dave Burke. Reunimos especialistas da plataforma Android para revelar todos os novos recursos do #Android11.",
            "A nova série de podcasts reúne os mais recentes insights, histórias e discussões de especialistas do setor. Os tópicos variam de engajamento responsável, conselhos de especialistas em fusões, aquisições e capital de risco a privacidade e acessibilidade.",
            "O Android é uma pilha de software com base em Linux de código aberto criada para diversos dispositivos e fatores de forma. O diagrama a seguir mostra a maioria dos componentes da plataforma Android.",
            "Com a capacidade de publicar rapidamente para mais de dois bilhões de dispositivos Android ativos, o Google Play ajuda você a aumentar o público global dos seus apps e jogos e a gerar receita.",
            "Aproveite as tecnologias mais recentes do Google com um conjunto único de APIs, distribuídas para dispositivos Android no mundo todo como parte do Google Play Services."
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
