package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.multidex.BuildConfig
import java.io.File


class IntentHelper {

    companion object {

        fun showPhoto(context: Context, photoUri: Uri){
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(photoUri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        }

        fun openImage(context: Context, imageFile: File){
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val photoURI: Uri = FileProvider.getUriForFile(
                context, "com.asusprojects.kotlincustomcomponents.fileprovider",
                imageFile
            )
            intent.setDataAndType(photoURI, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, "View using"))
        }

        fun openPdf(context: Context, pdfFile: File){
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val fileURI: Uri = FileProvider.getUriForFile(
                context, "com.asusprojects.kotlincustomcomponents.fileprovider",
                pdfFile
            )
            intent.setDataAndType(fileURI, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, "View using"))
        }

        fun openVideoFile(context: Context, videoFile: File){
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val fileURI: Uri = FileProvider.getUriForFile(
                context, "com.asusprojects.kotlincustomcomponents.fileprovider",
                videoFile
            )
            intent.setDataAndType(fileURI, "video/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, "View using"))
        }

    }
}