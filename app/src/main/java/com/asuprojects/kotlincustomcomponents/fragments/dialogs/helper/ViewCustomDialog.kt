package com.asuprojects.kotlincustomcomponents.fragments.dialogs.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import com.asuprojects.kotlincustomcomponents.R


class ViewCustomDialog {

    fun showDialog(activity: Activity){

        val dialog = Dialog(activity)
        //val dialog = dialogBuilder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog_view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val mDialogNo = dialog.findViewById<FrameLayout>(R.id.frmNo)
        mDialogNo.setOnClickListener {
            Toast.makeText(activity,"Cancel" ,Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val mDialogOk  = dialog.findViewById<FrameLayout>(R.id.frmOk)
        mDialogOk.setOnClickListener {
            Toast.makeText(activity,"Okay" ,Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

        dialog.show()
    }
}