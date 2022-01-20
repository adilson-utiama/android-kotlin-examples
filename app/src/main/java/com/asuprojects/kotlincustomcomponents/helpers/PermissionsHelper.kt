package com.asuprojects.kotlincustomcomponents.helpers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionsHelper {

    companion object {

        fun validatePermissions(activity: FragmentActivity, permissions: List<String>, requestCode: Int): Boolean {
            val permissionList = mutableListOf<String>()
            permissions.forEach { permission ->
                val hasPermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
                if(!hasPermission) permissionList.add(permission)
            }
            if(permissionList.isEmpty()) return true

            val requestPermissions = permissionList.toTypedArray()

            ActivityCompat.requestPermissions(activity, requestPermissions, requestCode )

            return true
        }

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