package com.asuprojects.kotlincustomcomponents.screens.menupopup

import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.asuprojects.kotlincustomcomponents.databinding.ActivityMenuPopupBinding
import com.asuprojects.kotlincustomcomponents.screens.menupopup.adapter.ChatAdapter
import com.asuprojects.kotlincustomcomponents.screens.menupopup.widget.MenuEditText
import com.asuprojects.kotlincustomcomponents.screens.menupopup.widget.SoftKeyBoardPopup

class MenuPopupActivity : AppCompatActivity(), MenuEditText.PopupListener {

    private lateinit var binding: ActivityMenuPopupBinding

    private lateinit var menuKeyboard: SoftKeyBoardPopup

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "PopupMenu like WhatsApp"
            setDisplayHomeAsUpEnabled(true)
        }

        initDummyChat()

        binding.editText.popupListener = this

        menuKeyboard = SoftKeyBoardPopup(
            this,
            binding.rootView,
            binding.editText,
            binding.editText,
            binding.menuChatContainer
        )

        binding.menuChat.setOnClickListener {
            toggle()
        }

    }


    private fun toggle() {
        if (menuKeyboard.isShowing) {
            menuKeyboard.dismiss()
        } else {
            menuKeyboard.show()
        }
    }

    private fun initDummyChat() {
        with(binding.rvChat) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MenuPopupActivity, LinearLayoutManager.VERTICAL, true)
            adapter = ChatAdapter()
        }
    }

    override fun onDestroy() {
        menuKeyboard.clear()
        super.onDestroy()
    }

    override fun getPopup(): PopupWindow {
        return menuKeyboard
    }


}