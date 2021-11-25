package com.asuprojects.kotlincustomcomponents.fragments.storage


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.CustomBSDialogInfo
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.fragment_storage.*
import java.io.File
import java.io.FileWriter


class StorageFragment : Fragment() {

    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publicExternalStorage.setOnClickListener {

            if(someText.text!!.isNotEmpty()){
                try{
                    if(ExternalStorageUtil.isExternalStorageMounted()){
                        //Check wheter this app has write external storage permission or not
                        val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )

                        //If do not grant write external storage permission
                        if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
                            //Request user to grant write external storage permission
                            ActivityCompat.requestPermissions(requireActivity(),
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION)
                        }else{
                            //Save android_studio_public.txt file to /storage/emulated/0/download folder
                            val publicDocDirPath =
                                ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DOWNLOADS)

                            val newFile = File(publicDocDirPath, "android_studio_public.txt")

                            val fileWriter = FileWriter(newFile)

                            fileWriter.write(someText.text.toString())
                            fileWriter.flush()
                            fileWriter.close()

                            val info = CustomBSDialogInfo()
                            info.setMsg("Save to Public External Storage Success.\nFile Path ${newFile.absolutePath}")
                            info.show(requireActivity().supportFragmentManager, info.tag)

                        }
                    }
                }catch(ex: Exception) {
                    val info = CustomBSDialogInfo()
                    info.setMsg("Save to Public External Storage Failed.\nError msg is ${ex.message}")
                    info.show(requireActivity().supportFragmentManager, info.tag)
                }
            }else{
                ToastUtil.msg(requireActivity(), "Necessario digitar um texto!")
            }
        }

        privateExternalStorage.setOnClickListener {
            if(someText.text!!.isNotEmpty()){
                try{
                    if(ExternalStorageUtil.isExternalStorageMounted()){
                        //Check wheter this app has write external storage permission or not
                        val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )

                        //If do not grant write external storage permission
                        if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
                            //Request user to grant write external storage permission
                            ActivityCompat.requestPermissions(requireActivity(),
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION)
                        }else{
                            //Save android_studio_private.txt file to /storage/emulated/0/Android/data/your_app_package_name/Files folder
                            val privateDirPath =
                                ExternalStorageUtil.getPrivateExternalStorageBaseDir(requireActivity(), null)

                            val newFile = File(privateDirPath, "android_studio_private.txt")

                            val fileWriter = FileWriter(newFile)

                            fileWriter.write(someText.text.toString())
                            fileWriter.flush()
                            fileWriter.close()

                            val info = CustomBSDialogInfo()
                            info.setMsg("Save to Private External Storage Success.\nFile Path ${newFile.absolutePath}")
                            info.show(requireActivity().supportFragmentManager, info.tag)

                        }
                    }
                }catch(ex: Exception) {
                    val info = CustomBSDialogInfo()
                    info.setMsg("Save to Private External Storage Failed.\nError msg is ${ex.message}")
                    info.show(requireActivity().supportFragmentManager, info.tag)
                }
            }else{
                ToastUtil.msg(requireActivity(), "Necessario digitar um texto!")
            }
        }

        privateCachedExternalStorage.setOnClickListener {
            if(someText.text!!.isNotEmpty()){
                try{
                    if(ExternalStorageUtil.isExternalStorageMounted()){
                        //Check wheter this app has write external storage permission or not
                        val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )

                        //If do not grant write external storage permission
                        if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
                            //Request user to grant write external storage permission
                            ActivityCompat.requestPermissions(requireActivity(),
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION)
                        }else{
                            //Save android_studio_private_cache.txt file to /storage/emulated/0/Android/data/your_app_package_name/cache folder
                            val privateDirPath =
                                ExternalStorageUtil.getPrivateCacheExternalStoragebaseDir(requireActivity())

                            val newFile = File(privateDirPath, "android_studio_private_cache.txt")

                            val fileWriter = FileWriter(newFile)

                            fileWriter.write(someText.text.toString())
                            fileWriter.flush()
                            fileWriter.close()

                            val info = CustomBSDialogInfo()
                            info.setMsg("Save to Private Cache Success.\nFile Path ${newFile.absolutePath}")
                            info.show(requireActivity().supportFragmentManager, info.tag)

                        }
                    }
                }catch(ex: Exception) {
                    val info = CustomBSDialogInfo()
                    info.setMsg("Save to Private Cache Failed.\nError msg is ${ex.message}")
                    info.show(requireActivity().supportFragmentManager, info.tag)
                }
            }else{
                ToastUtil.msg(requireActivity(), "Necessario digitar um texto!")
            }
        }

        displayExternalStoragePath.setOnClickListener {
            //Because create custom directory in public external storage folder need write permission
            //So we should grant the permission to the app first

            val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
                //Request user to grant write external storage permission
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION)
            }else{
                //Use Environment class to get public external storage directory
                val publicDir = Environment.getExternalStorageDirectory()

                //Because st the begining of this method, we had grant write external storage permission
                //So we can create this directory here
                val customPublicDir = File(publicDir, "android_studio_custom")
                customPublicDir.mkdirs()

                val musicPublicDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                val dcimPublicDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

                //Use Context class to get app private external storage directory
                val context = requireContext()

                val privateDir = context.getExternalFilesDir(null)
                val musicPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                val dcimPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
                val customPrivateDir = context.getExternalFilesDir("Custom")
                val cachedPrivateDir = context.externalCacheDir


                val builder = StringBuilder()
                builder.append("Music Pulic Directory: ").append("\n").append("->").append(musicPublicDir)
                builder.append("\n\n")
                builder.append("DCIM Public Directory: ").append("\n").append("->").append(dcimPublicDir)
                builder.append("\n\n")
                builder.append("Private Directory: ").append("\n").append("->").append(privateDir)
                builder.append("\n\n")
                builder.append("DCIM Private Directory: ").append("\n").append("->").append(dcimPrivateDir)
                builder.append("\n\n")
                builder.append("Music Private Directory: ").append("\n").append("->").append(musicPrivateDir)
                builder.append("\n\n")
                builder.append("Custom Private Directory: ").append("\n").append("->").append(customPrivateDir)
                builder.append("\n\n")
                builder.append("CAched Private Directory: ").append("\n").append("->").append(cachedPrivateDir)
                builder.append("\n")

                text_display.text = builder.toString()

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            val grantResultsSize = grantResults.size
            if(grantResultsSize > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val info = CustomBSDialogInfo()
                info.setMsg("You Grant write external storage permission permission. Please click button again to continue")
                info.show(requireActivity().supportFragmentManager, info.tag)
            }else{
                val info = CustomBSDialogInfo()
                info.setMsg("You Denied write external storage permission")
                info.show(requireActivity().supportFragmentManager, info.tag)
            }
        }
    }

}
