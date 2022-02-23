package com.asuprojects.kotlincustomcomponents.screens.menupopup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.screens.menupopup.util.ChatUtil
import kotlinx.android.synthetic.main.item_chat.view.*
import java.security.MessageDigest

class ChatAdapter : RecyclerView.Adapter<ItemChat>() {

    val chats = ChatUtil.createDummy()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemChat {
        return ItemChat.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemChat, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int {
        return chats.size
    }

}

class ItemChat(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(chat: Chat) {
        with(itemView) {
            tvMsg.text = chat.message
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int) : ItemChat {
            return ItemChat(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_chat, parent, false
                )
            )
        }
    }
}

data class Chat(val message: String)