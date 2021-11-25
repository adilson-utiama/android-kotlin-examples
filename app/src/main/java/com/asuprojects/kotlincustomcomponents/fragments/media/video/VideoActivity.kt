package com.asuprojects.kotlincustomcomponents.fragments.media.video

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.media.MediaViewAdapter
import com.asuprojects.kotlincustomcomponents.helpers.IntentHelper
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_video.*
import java.io.File
import java.io.IOException
import java.util.*

class VideoActivity : AppCompatActivity() {

    private var videos = mutableListOf<String>()
    private lateinit var adapter: MediaViewAdapter

    private var videoFilesDir: String? = ""
    val REQUEST_VIDEO_CAPTURE = 2
    val REQUEST_RECORD_VIDEO_PERMISSION = 200

    private var currentVideoPath: String = ""

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        supportActionBar?.apply {
            this.title = "Videos"
            this.setDisplayHomeAsUpEnabled(true)
        }

        requestCameraPermission()

        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        if (!externalFilesDir!!.exists()) {
            externalFilesDir.mkdirs()
        }

        externalFilesDir.apply {
            this.list { _, name ->
                videos.add(name)
            }
            videoFilesDir = externalFilesDir.absolutePath
            text_video_files_dir.text = externalFilesDir.absolutePath

            adapter = MediaViewAdapter(videos)

            adapter.setOnMediaFileSelected(object : MediaViewAdapter.OnMediaFileSelected {
                override fun onClickMediaFile(view: View, position: Int) {
                    val filePath = "$videoFilesDir/${videos[position]}"

                    val file = File(filePath)
                    if (file.exists()) {
                        IntentHelper.openVideoFile(this@VideoActivity, file)
                    }

                }

                override fun onClickMediaOptionsMenu(view: View, position: Int) {
                    val popupMenu = PopupMenu(this@VideoActivity, view)
                    popupMenu.menuInflater.inflate(R.menu.popup_menu_audio_options, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.popmenu_audio_delete -> {
                                val filePath = "$videoFilesDir/${videos[position]}"
                                val file = File(filePath)
                                file.delete()
                                adapter.notifyItemRemoved(position)
                                videos.removeAt(position)
                            }
                        }
                        true
                    }
                    popupMenu.show()
                }
            })
            recycler_videos.adapter = adapter
        }

        fab_record_video.setOnClickListener {

            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                takeVideoIntent.resolveActivity(packageManager)?.also {

                    val videoFile: File? = try {
                        createVideoFile()
                    } catch (e: IOException) {
                        null
                    }

                    videoFile?.also {
                        val videoUri: Uri = FileProvider.getUriForFile(
                            this@VideoActivity,
                            "com.asusprojects.kotlincustomcomponents.fileprovider",
                            it
                        )
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                    }

                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri? = data?.data

            Log.i("TESTE", "VideoUri: ${videoUri.toString()}")

            val split = currentVideoPath.split("/")
            val fileName = split[split.size - 1]

            videos.add(fileName)
            adapter.notifyDataSetChanged()

//            videoUri?.apply {
//                val split = this.toFile().absolutePath.split("/")
//                val fileName = split[split.size - 1]
//
//                videos.add(fileName)
//                adapter.notifyDataSetChanged()
//            }


        }

    }

    @Throws(IOException::class)
    private fun createVideoFile(): File {
        val storageDir: File? = File(videoFilesDir!!)
        return File.createTempFile(
            "video_", /* prefix */
            ".mp4", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentVideoPath = absolutePath
        }
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@VideoActivity,
                Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(
                this@VideoActivity,
                "Permission Required to Access Camera",
                Toast.LENGTH_SHORT
            ).show()
        }

        ActivityCompat.requestPermissions(
            this@VideoActivity,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_RECORD_VIDEO_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_VIDEO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtil.msg(this@VideoActivity, "Permission granted!! Now you can use Camera")
            } else {
                ToastUtil.msg(this@VideoActivity, "Oops, You just denied the Camera Permission!")
            }
        }
    }
}
