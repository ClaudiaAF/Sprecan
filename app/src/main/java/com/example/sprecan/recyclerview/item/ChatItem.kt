package com.example.sprecan.recyclerview.item

import android.content.Context
import com.example.sprecan.R
import com.example.sprecan.glide.GlideApp
import com.example.sprecan.model.Chat
import com.example.sprecan.utils.Storage
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_layout.*
import kotlinx.android.synthetic.main.chat_layout.view.*

class ChatItem(
    val chat: Chat,
    private val context: Context
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = chat.chatName

        if (!chat.profilePicturePath.isNullOrBlank())
            GlideApp.with(context)
                .load(Storage.pathToReference(chat.profilePicturePath))
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(viewHolder.user_profile_img_card)
    }

    override fun getLayout() = R.layout.chat_layout
}