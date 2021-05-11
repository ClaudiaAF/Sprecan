package com.example.sprecan.utils

import android.media.session.MediaSessionManager
import android.widget.Toast
import com.example.sprecan.AuthenticationActivity
import com.example.sprecan.ChatPage
import com.example.sprecan.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {

    //initiate our firestore
    private val db = FirebaseFirestore.getInstance()

    //add user to firestore when they register
    fun registerUser(activity: AuthenticationActivity, userInfo: User){
        //adding to firestore
        db.collection(Constants.USERS)
                .document(userInfo.id)
                .set(userInfo, SetOptions.merge()) //merge new user info into document (update existing user info)
                .addOnSuccessListener {
                    //calling function in our activity to inform that register was successful
                    activity.registerUserSuccess(userInfo.id)
                }
                .addOnFailureListener {
                    //call activity function to show error
                    activity.showErrorSnackBar("Error while registering user", true)
                }
    }

    fun getUserInfoById(activity: ChatPage, uid: String){
        db.collection(Constants.USERS)
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if(document != null) {

                        val user = document.toObject(User::class.java)!!

                        activity.setUserInfo(user)

                    } else {
                        Toast.makeText(activity, "error loading user info", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.message, Toast.LENGTH_SHORT).show()
                }
    }

}