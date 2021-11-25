package com.asuprojects.kotlincustomcomponents.fragments.media.audio

import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.media.MediaViewAdapter
import kotlinx.android.synthetic.main.activity_audio.*
import java.io.File

class AudioActivity : AppCompatActivity() {

    private var audios = mutableListOf<String>()
    private lateinit var adapter: MediaViewAdapter

    private var audioFilesDir: String? = ""

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)

        supportActionBar?.apply {
            this.title = "Audios"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)

        externalFilesDir?.apply {
            this.list { _, name ->
                audios.add(name)
            }
            audioFilesDir = externalFilesDir.absolutePath
            text_audio_files_dir.text = externalFilesDir.absolutePath

            adapter =
                MediaViewAdapter(
                    audios
                )
            adapter.setOnMediaFileSelected(object : MediaViewAdapter.OnMediaFileSelected {
                override fun onClickMediaFile(view: View, position: Int) {
                    val filePath = "$audioFilesDir/${audios[position]}"

                    val file = File(filePath)
                    if(file.exists()){
                        //ToastUtil.msg(this@AudioActivity, "FilePathResolver: ${file.absolutePath}")
                        val audioPlayer = CustomAudioPlayer(file)
                        audioPlayer.show(supportFragmentManager, audioPlayer.tag)
                    }

                }

                override fun onClickMediaOptionsMenu(view: View, position: Int) {
                    val popupMenu = PopupMenu(this@AudioActivity, view)
                    popupMenu.menuInflater.inflate(R.menu.popup_menu_audio_options, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.popmenu_audio_delete -> {
                                val filePath = "$audioFilesDir/${audios[position]}"
                                val file = File(filePath)
                                file.delete()
                                adapter.notifyItemRemoved(position)
                                audios.removeAt(position)
                            }
                        }
                        true
                    }
                    popupMenu.show()
                }
            })
            recycler_audios.adapter = adapter

            fab_record_audio.setOnClickListener{
                val recorder = CustomAudioRecorder(audioFilesDir!!)
                //recorder.setDefaultFilesDir()
                recorder.setOnRecordAudio(object: OnRecordAudio{
                    override fun onFinishRecordAudio(filePath: String) {
                        val split = filePath.split("/")
                        val fileName = split[split.size - 1]
                        audios.add(fileName)
                        adapter.notifyDataSetChanged()
                    }
                })
                recorder.show(supportFragmentManager, recorder.tag)
            }
        }

    }
}
