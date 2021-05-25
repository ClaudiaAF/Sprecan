package com.example.sprecan

import android.content.Intent
import com.example.sprecan.model.User
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class AuthenticationActivity : BaseActivity() {


    fun registerUser(email: String, password: String, name: String) {

        if (email == "" || password == "") {
            showErrorSnackBar("Please enter all your details", true)
        } else {
            //create an instance of firebase auth and create a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

//                                        showErrorSnackBar("Succesfully registered user id ${firebaseUser.uid}", false)

                                    //creates user data
                                    val user = User(
                                            firebaseUser.uid,
                                            name,
                                            email,
                                            ""
                                    )

                                    //call firestore adding function
                                    Firestore().registerUser(this, user)

                                } else {
                                    showErrorSnackBar(task.exception!!.message.toString(), true)
                                }
                            }
                    )
        }
    }


    fun loginUser(email: String, password: String) {

        if (email == "" || password == "") {
            showErrorSnackBar("Please enter your email and password", true)
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { signInListener ->

                                if (signInListener.isSuccessful) {
                                    val firebaseUser: FirebaseUser = signInListener.result!!.user!!

//                                        showErrorSnackBar("Succesfully logged in user id ${firebaseUser.uid}", false)

                                    loginUserSuccess(firebaseUser.uid)

                                } else {
                                    showErrorSnackBar(signInListener.exception!!.message.toString(), true)
                                }
                            }
                    )
        }
    }

    //call to navigate to our next page
    fun registerUserSuccess(uid: String) {
        //TODO: look into using inprogress bar/dialogue

        showErrorSnackBar("succcess on registration", false)

        //navigation
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.LOGGED_IN_ID, uid) //sending activity
        startActivity(intent)
        finish()
        //TODO: SharedPreferences
    }

    private fun loginUserSuccess(uid: String) {
        //TODO: SharedPreferences
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.LOGGED_IN_ID, uid) //sending activity
        startActivity(intent)
        finish()
    }


}
