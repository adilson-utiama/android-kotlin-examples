package com.asuprojects.kotlincustomcomponents.fragments.dialogs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.dialogs.helper.ViewCustomDialog
import kotlinx.android.synthetic.main.fragment_alert_dialogs.*


class AlertDialogsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_dialogs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_alertdialog_1.setOnClickListener {
            val dialog = AlertDialog.Builder(requireActivity())
            val viewDialog = LayoutInflater.from(requireActivity())
                .inflate(R.layout.custom_alert_dialog_view, main_container_alert_dialog, false)
            val buttonOk = viewDialog.findViewById<AppCompatButton>(R.id.buttonOk)
            dialog.setView(viewDialog)
            val alertDialog = dialog.create()
            buttonOk.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()

        }

        btn_alertdialog_2.setOnClickListener {
            val viewCustomDialog =
                ViewCustomDialog()
            viewCustomDialog.showDialog(requireActivity())
        }

        btn_alertdialog_3.setOnClickListener {
            val dialogFragment = CustomDialogFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "dialogFragment")
        }

        btn_alertdialog_4.setOnClickListener {
            val animationFragment = CustomDialogAnimationFragment()
            animationFragment.show(requireActivity().supportFragmentManager, "animationFragment")
        }

        btn_alertdialog_full.setOnClickListener {
            DialogFullScreen().show(requireActivity().supportFragmentManager, "FullScreen")
        }
    }
}
