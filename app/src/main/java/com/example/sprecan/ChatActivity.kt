package com.example.sprecan

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sprecan.model.Chat
import com.example.sprecan.model.Message
import com.example.sprecan.model.User
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.layout_chat_view.*
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesListenerRegistration: ListenerRegistration
    private lateinit var messageSection: Section
    private var shouldInitRecyclerView = true
    var currentUser: User? = null
    var contactUser: User? = null
    var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chat_view)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val userId = intent.getStringExtra(Constants.LOGGED_IN_ID)
        val userName = intent.getStringExtra(Constants.USER_NAME)
        chatId = intent.getStringExtra(Constants.CHAT_ID)
        val contactId = intent.getStringExtra(Constants.CONTACT_ID)

        if (chatId != null) {
            messagesListenerRegistration =
                Firestore().getChatMessages(chatId!!, this::updateRecyclerView)
        } else if (userId != null && contactId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection(Constants.USERS)
                .document(userId)
                .collection(Constants.USER_CHATS)
                .whereEqualTo("contactId", contactId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result?.documents

                        if (documents!!.isNotEmpty()) {
                            val chat = documents.first().toObject(Chat::class.java)!!
                            chatId = chat.chatId
                            messagesListenerRegistration =
                                Firestore().getChatMessages(
                                    chatId!!,
                                    this::updateRecyclerView
                                )
                        } else {
                            db.collection(Constants.USERS)
                                .document(userId)
                                .get()
                                .addOnCompleteListener { mainUserTask ->
                                    if (mainUserTask.isSuccessful) {
                                        val mainUserDoc = mainUserTask.result
                                        currentUser =
                                            mainUserDoc!!.toObject(User::class.java)!!

                                        db.collection(Constants.USERS)
                                            .document(contactId)
                                            .get()
                                            .addOnCompleteListener { contactUserTask ->
                                                if (contactUserTask.isSuccessful) {
                                                    val contactUserDoc =
                                                        contactUserTask.result
                                                    contactUser =
                                                        contactUserDoc!!.toObject(User::class.java)!!

                                                    if (currentUser != null && contactUser != null) {
                                                        val newChat =
                                                            db.collection("chats")
                                                                .add(mapOf("archive" to false))
                                                                .addOnSuccessListener { documentReference ->
                                                                    db.collection(Constants.USERS)
                                                                        .document(currentUser!!.id)
                                                                        .collection(Constants.USER_CHATS)
                                                                        .document()
                                                                        .set(
                                                                            mapOf(
                                                                                "chatId" to documentReference.id,
                                                                                "chatName" to contactUser!!.name,
                                                                                "contactId" to contactUser!!.id
                                                                            )
                                                                        )

                                                                    db.collection(Constants.USERS)
                                                                        .document(contactUser!!.id)
                                                                        .collection(Constants.USER_CHATS)
                                                                        .document()
                                                                        .set(
                                                                            mapOf(
                                                                                "chatId" to documentReference.id,
                                                                                "chatName" to currentUser!!.name,
                                                                                "contactId" to currentUser!!.id
                                                                            )
                                                                        )
                                                                    chatId = documentReference.id
                                                                    messagesListenerRegistration =
                                                                        Firestore().getChatMessages(
                                                                            chatId!!,
                                                                            this::updateRecyclerView
                                                                        )
                                                                }
                                                    }
                                                }
                                            }
                                    }
                                }
                        }
                    }
                }

        }

        if (userName != null) {
            username_chat_view.text = userName
        }

        imageView_send.setOnClickListener {
            val messageToSend =
                Message(
                    chatId!!,
                    userId.toString(),
                    editText_message.text.toString(),
                    Calendar.getInstance().time
                )
            editText_message.setText("")
            Firestore().sendMessage(messageToSend, chatId!!)
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

    private fun updateRecyclerView(messages: List<Item>) {
        fun init() {
            recycler_view_messages.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = GroupAdapter<ViewHolder>().apply {
                    messageSection = Section(messages)
                    this.add(messageSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messageSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        val adapter = recycler_view_messages.adapter

        if (adapter != null) {
            recycler_view_messages.scrollToPosition(adapter.itemCount - 1)
        }
    }
}