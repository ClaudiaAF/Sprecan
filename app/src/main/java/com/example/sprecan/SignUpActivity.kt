package com.example.sprecan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.model.DatabaseId
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AuthenticationActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseId
    private var firebaseUserId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        val signInHere_button = findViewById<Button>(R.id.signIn_button)

        signInHere_button.setOnClickListener {

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_button.setOnClickListener {

            var email: String = et_email.text.toString().trim { it <= ' ' }
            var password: String = et_password.text.toString().trim { it <= ' ' }

            registerUser(email, password)

        }
    }


}