package com.example.sprecan

import android.content.Intent
import android.provider.SyncStateContract
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.layout_signinactvity.*

open class AuthenticationActivity : BaseActivity() {


        fun registerUser(email: String, password: String) {

            if (email == "" || password == "") {
                showErrorSnackBar("Please enter all your details", true)
            } else {
                //create an instance of firebase auth and create a user with email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                OnCompleteListener<AuthResult> { task ->

                                    if(task.isSuccessful){
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        showErrorSnackBar("Succesfully registered user id ${firebaseUser.uid}", false)

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
                                OnCompleteListener<AuthResult> { task ->

                                    if(task.isSuccessful){
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        showErrorSnackBar("Succesfully logged in user id ${firebaseUser.uid}", false)

                                    } else {
                                        showErrorSnackBar(task.exception!!.message.toString(), true)
                                    }
                                }
                        )
                }
            }


}
