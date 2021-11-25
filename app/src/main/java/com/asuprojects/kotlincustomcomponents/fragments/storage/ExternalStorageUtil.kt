package com.asuprojects.kotlincustomcomponents.fragments.storage

import android.content.Context
import android.os.Environment
import android.os.StatFs

class ExternalStorageUtil {

    companion object {

        //Check wether the external storage is mounted or not
        fun isExternalStorageMounted(): Boolean {
            val dirState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == dirState
        }

        //Check wether the external storage is read only or not
        fun isExternalStorageReadOnly(): Boolean {
            val dirState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED_READ_ONLY == dirState
        }

        //Get private external storage base directory
        fun getPrivateExternalStorageBaseDir(context: Context, dirType: String?): String? {
            var ret: String? = ""
            if(isExternalStorageMounted()){
                val file = context.getExternalFilesDir(dirType)
                ret = file?.absolutePath
            }
            return ret
        }

        //Get private cache external storage base directory
        fun getPrivateCacheExternalStoragebaseDir(context: Context): String? {
            var ret: String? = ""
            if(isExternalStorageMounted()){
                val file = context.externalCacheDir
                ret = file?.absolutePath
            }
            return ret
        }

        //Get public external storage base directory
        @Deprecated("Environment.getExternalStorageDirectory Deprecated")
        fun getPublicExternalStorageBaseDir(): String? {
            var ret: String? = ""
            if(isExternalStorageMounted()){
                val file = Environment.getExternalStorageDirectory()
                ret = file?.absolutePath
            }
            return ret
        }

        //Get external storage base directory
        @Deprecated("Environment.getExternalStoragePublicDirectory(dirType) Deprecated")
        fun getPublicExternalStorageBaseDir(dirType: String): String? {
            var ret: String? = ""
            if(isExternalStorageMounted()){
                val file =
                    Environment.getExternalStoragePublicDirectory(dirType)
                ret = file?.absolutePath
            }
            return ret
        }

        //Get external storage disk space, return in MB
        fun getExternalStorageSpace(): Long {
            var ret: Long = 0
            if(isExternalStorageMounted()){
                val fileState = StatFs(getPublicExternalStorageBaseDir())

                //Get total block count
                val count = fileState.blockCountLong

                //Get each block size
                val size = fileState.blockSizeLong

                //Calculate total space size
                ret = ((count * size) / 1024) / 1024
            }
            return ret
        }

        //Get external storage left free disk space, return in MB
        fun getExternalStorageLeftSpace(): Long {
            var ret: Long = 0
            if(isExternalStorageMounted()){
                val fileState = StatFs(getPublicExternalStorageBaseDir())

                //Get total block count
                val count = fileState.freeBlocksLong

                //Get each block size
                val size = fileState.blockSizeLong

                //Calculate free space size
                ret = ((count * size) / 1024) / 1024
            }
            return ret
        }

        //Get external storage available disk space, return in MB
        fun getExternalStorageAvailableSpace(): Long {
            var ret: Long = 0
            if(isExternalStorageMounted()){
                val fileState = StatFs(getPublicExternalStorageBaseDir())

                //Get available block count
                val count = fileState.availableBlocksLong

                //Get each block size
                val size = fileState.blockSizeLong

                //Calculate available space size
                ret = ((count * size) / 1024) / 1024
            }
            return ret
        }


    }
}