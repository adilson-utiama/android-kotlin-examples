package com.asuprojects.kotlincustomcomponents.helpers

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.core.app.ActivityCompat

class PermissionsHelper {

    companion object {

        fun requestCameraPermission(activity: Activity, requestCode: Int) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(
                    activity,
                    "Permission Required to Access Camera",
                    Toast.LENGTH_SHORT
                ).show()
            }

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                requestCode
            )
        }

        fun requestWriteStoragePermission(activity: Activity, requestCode: Int) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    activity,
                    "Permission Required to Write External Storage",
                    Toast.LENGTH_SHORT
                ).show()
            }

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        }

    }
}