package com.example.sprecan.utils

import android.content.ContentValues.TAG
import android.content.Context
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

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(
                    FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                    "", null
                )
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }
    }

    fun updateCurrentUser(name: String = "", profilePicturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name

        if (profilePicturePath != null) {
            userFieldMap["profilePicturePath"] = profilePicturePath
        }

        updateChatProfilePictures(currentUserDocRef.id, profilePicturePath, name)
        currentUserDocRef.update(userFieldMap)
    }

    fun updateChatProfilePictures(userUid: String, profilePicturePath: String?, name: String) {
        val userFieldMap = mutableMapOf<String, Any>()

        if (!profilePicturePath.isNullOrBlank())
            userFieldMap["profilePicturePath"] = profilePicturePath

        if (!name.isBlank())
            userFieldMap["chatName"] = name

        db.collection(Constants.USERS)
            .whereNotEqualTo("id", userUid)
            .get()
            .addOnCompleteListener { usersTask ->
                if (usersTask.isSuccessful) {
                    val documents = usersTask.result?.documents

                    documents?.forEach { userDoc ->
                        db.collection(Constants.USERS)
                            .document(userDoc.id)
                            .collection(Constants.USER_CHATS)
                            .whereEqualTo("contactId", userUid)
                            .get()
                            .addOnCompleteListener { userChatTask ->
                                if (userChatTask.isSuccessful) {
                                    val chatDocuments = userChatTask.result?.documents

                                    chatDocuments?.forEach { chatDoc ->
                                        db.collection(Constants.USERS)
                                            .document(userDoc.id)
                                            .collection(Constants.USER_CHATS)
                                            .document(chatDoc.id)
                                            .update(userFieldMap)
                                    }
                                }
                            }
                    }
                }
            }
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)!!)
            }
    }


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


    fun getUserChats(
        context: Context,
        userUid: String,
        onListen: (List<Item>) -> Unit
    ): ListenerRegistration {
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
                    userChats.add(ChatItem(it.toObject(Chat::class.java)!!, context))
                    return@forEach
                }
                onListen(userChats)
            }
    }

    fun getContacts(
        context: Context,
        userUid: String,
        onListen: (List<Item>) -> Unit
    ): ListenerRegistration {
        return db.collection(Constants.USERS)
            .whereNotEqualTo("id", userUid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ContactListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val contacts = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    contacts.add(ContactItem(it.toObject(User::class.java)!!, context))
                    return@forEach
                }
                onListen(contacts)
            }
    }

    fun getUser(userId: String, onComplete: (User) -> Unit) {

        db.collection(Constants.USERS)
            .document(userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    onComplete(document!!.toObject(User::class.java)!!)
                }
            }
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