package com.example.sprecan.recyclerview.item

import com.example.sprecan.R
import com.example.sprecan.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_layout.*

class ContactItem(val user: User): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = user.name
        viewHolder.textView_altText.text = user.bio
    }

    override fun getLayout() = R.layout.chat_layout
}