package com.example.sprecan.recyclerview.item

import com.example.sprecan.R
import com.example.sprecan.model.Chat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_layout.*
import kotlinx.android.synthetic.main.chat_layout.view.*

class ChatItem(val chat: Chat): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = chat.chatName
        viewHolder.textView_altText.text = chat.latestMessage
    }

    override fun getLayout() = R.layout.chat_layout
}