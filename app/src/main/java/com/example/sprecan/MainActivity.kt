package com.example.sprecan


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sprecan.glide.GlideApp
import com.example.sprecan.recyclerview.item.ChatItem
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.example.sprecan.utils.Storage
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.main_activity_layout.user_profile_img_dash
import kotlinx.android.synthetic.main.profile_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private lateinit var chatListenerRegistration: ListenerRegistration
    private lateinit var chatSection: Section
    private var shouldInitRecyclerView = true
    private var userId: String? = ""
    private var pictureJustChanged = false

    override fun onStart() {
        super.onStart()
        Firestore().getCurrentUser { user ->
            if (!pictureJustChanged && user.profilePicturePath != null)
                GlideApp.with(this)
                    .load(Storage.pathToReference(user.profilePicturePath))
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(user_profile_img_dash)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)
//        centerTitle();

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if (userId != null) {
            chatListenerRegistration = Firestore().getUserChats(this, userId!!, this::updateRecyclerView)
        }

        btn_add_chat.setOnClickListener {
            val intent = Intent(this, ContactsActivity::class.java)
            intent.putExtra(Constants.LOGGED_IN_ID, userId)
            startActivity(intent)
        }

        user_profile_img_dash.setOnClickListener {
            val intent = Intent(this, UserAccount::class.java)
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
