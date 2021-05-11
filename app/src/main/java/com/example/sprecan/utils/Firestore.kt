package com.example.sprecan.utils

import android.media.session.MediaSessionManager
import com.example.sprecan.AuthenticationActivity
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
}