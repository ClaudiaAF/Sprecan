package com.example.sprecan.recyclerview.item

import android.content.Context
import com.example.sprecan.R
import com.example.sprecan.glide.GlideApp
import com.example.sprecan.model.User
import com.example.sprecan.utils.Storage
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_layout.*

class ContactItem(val user: User,
                    private val context: Context)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = user.name
        if (user.profilePicturePath != null)
            GlideApp.with(context)
                .load(Storage.pathToReference(user.profilePicturePath))
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(viewHolder.user_profile_img_card)
    }

    override fun getLayout() = R.layout.chat_layout
}