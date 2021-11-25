package com.asuprojects.kotlincustomcomponents.fragments.bottomsheet


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.CustomBSDialogInfo
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.CustomBSDialolgAddText
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.OnSaveText
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.PersistentBottomSheetActivity
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_bottom_sheets.*


class BottomSheetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_bottom_sheet_default.setOnClickListener {
            val bsView = layoutInflater.inflate(R.layout.default_bottom_sheet, null)

            val bottomSheetDialog = BottomSheetDialog(requireActivity())
            bottomSheetDialog.setContentView(bsView)
            bottomSheetDialog.show()

            bsView.findViewById<AppCompatTextView>(R.id.preview).setOnClickListener {
                bottomSheetDialog.dismiss()
                ToastUtil.msg(requireActivity(), "Preview Selected")
            }
            bsView.findViewById<AppCompatTextView>(R.id.share).setOnClickListener {
                bottomSheetDialog.dismiss()
                ToastUtil.msg(requireActivity(), "Share Selected")
            }
            bsView.findViewById<AppCompatTextView>(R.id.getlink).setOnClickListener {
                bottomSheetDialog.dismiss()
                ToastUtil.msg(requireActivity(), "Get Link Selected")
            }
            bsView.findViewById<AppCompatTextView>(R.id.copy).setOnClickListener {
                bottomSheetDialog.dismiss()
                ToastUtil.msg(requireActivity(), "Copy Selected")
            }
            bsView.findViewById<AppCompatTextView>(R.id.email).setOnClickListener {
                bottomSheetDialog.dismiss()
                ToastUtil.msg(requireActivity(), "Email Selected")
            }

        }

        btn_bottom_sheet_custom_bsd_fragment_info.setOnClickListener {
            val bsDialogInfo = CustomBSDialogInfo()
            bsDialogInfo.show(requireActivity().supportFragmentManager, bsDialogInfo.tag)
            bsDialogInfo.setMsg("Android é um sistema operacional baseado no núcleo Linux, " +
                    "desenvolvido por um consorcio de desenvolvedores conhecido como Open Handset Alliance, " +
                    "sendo o principal colaborador o Google.")
        }

        btn_bottom_sheet_custom_bsd_fragment_edit.setOnClickListener {
            val bsDialolgAddText = CustomBSDialolgAddText()
            bsDialolgAddText.show(requireActivity().supportFragmentManager, "CustomBottomSheet")
            bsDialolgAddText.isCancelable = false
            bsDialolgAddText.setOnSaveText(object: OnSaveText{
                override fun onClickSaveTextButton(text: String) {
                    ToastUtil.msg(requireActivity(), text)
                }
            })
        }

        btn_bottom_sheet_persistent.setOnClickListener {
            startActivity(Intent(requireActivity(), PersistentBottomSheetActivity::class.java))
        }
    }

}
