package com.example.sprecan.utils

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.example.sprecan.AuthenticationActivity
import com.example.sprecan.ChatActivity
import com.example.sprecan.MainActivity
import com.example.sprecan.model.Chat
import com.example.sprecan.model.Message
import com.example.sprecan.model.User
import com.example.sprecan.recyclerview.item.ChatItem
import com.example.sprecan.recyclerview.item.ContactItem
import com.example.sprecan.recyclerview.item.MessageItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.xwray.groupie.kotlinandroidextensions.Item

class Firestore {

    //initiate our firestore
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val currentUserDocRef: DocumentReference
        get() = db.document(
            "users/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}"
        )


    //add user to firestore when they register
    fun registerUser(activity: AuthenticationActivity, userInfo: User) {
        //adding to firestore
        db.collection(Constants.USERS)
            .document(userInfo.id)
            .set(
                userInfo,
                SetOptions.merge()
            ) //merge new user info into document (update existing user info)
            .addOnSuccessListener {
                //calling function in our activity to inform that register was successful
                activity.registerUserSuccess(userInfo.id)
            }
            .addOnFailureListener {
                //call activity function to show error
                activity.showErrorSnackBar("Error while registering user", true)
            }
    }

    fun getUserInfoById(activity: MainActivity, uid: String) {
        db.collection(Constants.USERS)
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val user = document.toObject(User::class.java)!!

//                        activity.setUserInfo(user)

                } else {
                    Toast.makeText(activity, "error loading user info", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun updateCurrentUser(name: String = "", bio: String = "", profilePicturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePicturePath != null)
            userFieldMap["profilePicturePath"] = profilePicturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun getUserChats(userUid: String, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return db.collection(Constants.USERS)
            .document(userUid)
            .collection(Constants.USER_CHATS)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "UserChatsListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val userChats = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    userChats.add(ChatItem(it.toObject(Chat::class.java)!!))
                    return@forEach
                }
                onListen(userChats)
            }
    }

    fun getContacts(userUid: String, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return db.collection(Constants.USERS)
            .whereNotEqualTo("id", userUid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ContactListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val contacts = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    contacts.add(ContactItem(it.toObject(User::class.java)!!))
                    return@forEach
                }
                onListen(contacts)
            }
    }

    fun getUser(userId: String, onComplete: (User) -> Unit) {

        db.collection(Constants.USERS)
            .document(userId)
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val document = task.result
                    onComplete(document!!.toObject(User::class.java)!!)
                }
            }
    }

    fun getContactChat(userId: String, contactId: String, onComplete: (Chat?) -> Unit) {

        db.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.USER_CHATS)
            .whereEqualTo("contactId", contactId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    onComplete(querySnapshot.documents.first().toObject(Chat::class.java)!!)
                } else {
                    onComplete(null)
                }
            }

        onComplete(null)
    }

    fun createChat(currentUser: User, contactUser: User) {
        val newChat = db.collection("chats").document()
        newChat.set(mapOf("archive" to false))

        db.collection(Constants.USERS)
            .document(currentUser.id)
            .collection(Constants.USER_CHATS)
            .document()
            .set(
                mapOf(
                    "chatId" to newChat.id,
                    "chatName" to contactUser.name,
                    "contactId" to contactUser.id
                )
            )

        db.collection(Constants.USERS)
            .document(contactUser.id)
            .collection(Constants.USER_CHATS)
            .document()
            .set(
                mapOf(
                    "chatId" to newChat.id,
                    "chatName" to currentUser.name,
                    "contactId" to currentUser.id
                )
            )

    }

    fun getChatMessages(chatId: String, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return db.collection(Constants.MESSAGES)
            .whereEqualTo(Constants.CHAT_ID, chatId)
            .orderBy("sentDate")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "getChatMessages error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val messages = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    messages.add(MessageItem(it.toObject(Message::class.java)!!))
                    return@forEach
                }
                onListen(messages)
            }
    }

    fun sendMessage(message: Message, chatId: String) {
        db.collection("messages").add(message)
    }

}