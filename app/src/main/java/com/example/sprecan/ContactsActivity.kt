package com.example.sprecan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sprecan.recyclerview.item.ChatItem
import com.example.sprecan.recyclerview.item.ContactItem
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.contact_layout.*
import kotlinx.android.synthetic.main.main_activity_layout.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var contactListenerRegistration: ListenerRegistration
    private lateinit var contactSection: Section
    private var shouldInitRecyclerView = true
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_layout)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if (userId != null) {
            contactListenerRegistration = Firestore().getContacts(this, userId!!, this::updateRecyclerView)
        }

    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    private fun updateRecyclerView(chats: List<Item>) {
        fun init() {
            recycler_view_contacts.apply {
                layoutManager = LinearLayoutManager(this@ContactsActivity)
                adapter = GroupAdapter<ViewHolder>().apply {
                    contactSection = Section(chats)
                    this.add(contactSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = contactSection.update(chats)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        val adapter = recycler_view_contacts.adapter

        if (adapter != null) {
            recycler_view_contacts.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private  val onItemClick = OnItemClickListener { item, view ->
        if (item is ContactItem) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constants.LOGGED_IN_ID, userId)
            intent.putExtra(Constants.USER_NAME, item.user.name)
            intent.putExtra(Constants.CONTACT_ID, item.user.id)
            startActivity(intent)
        }
    }
}


