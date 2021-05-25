package com.example.sprecan


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sprecan.recyclerview.item.ChatItem
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_activity_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private lateinit var chatListenerRegistration: ListenerRegistration
    private lateinit var chatSection: Section
    private var shouldInitRecyclerView = true
    private var userId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)
//        centerTitle();

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //TODO: think about using shared preferences instead of getStringExtra
        userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if (userId != null) {
            chatListenerRegistration = Firestore().getUserChats(userId!!, this::updateRecyclerView)
        }

        btn_add_chat.setOnClickListener {
            val intent = Intent(this, ContactsActivity::class.java)
            intent.putExtra(Constants.LOGGED_IN_ID, userId)
            startActivity(intent)
        }
    }

    private fun updateRecyclerView(chats: List<Item>) {
        fun init() {
            recycler_view_chats.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = GroupAdapter<ViewHolder>().apply {
                    chatSection = Section(chats)
                    this.add(chatSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = chatSection.update(chats)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        val adapter = recycler_view_chats.adapter

        if (adapter != null) {
            recycler_view_chats.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private  val onItemClick = OnItemClickListener { item, view ->
        if (item is ChatItem) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constants.LOGGED_IN_ID, userId!!)
            intent.putExtra(Constants.CHAT_ID, item.chat.chatId)
            intent.putExtra(Constants.USER_NAME, item.chat.chatName)
            startActivity(intent)
        }
    }

}
