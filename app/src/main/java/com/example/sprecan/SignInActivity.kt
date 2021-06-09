package com.example.sprecan

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.login_button
import kotlinx.android.synthetic.main.layout_signinactvity.*
import org.jetbrains.anko.design.longSnackbar

class SignInActivity : AuthenticationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_signinactvity)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val signUpHere_button = findViewById<Button>(R.id.signUp_button)

        signUpHere_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        login_button.setOnClickListener {
            var email: String = et_loginEmail.text.toString().trim{ it <= ' '}
            var password: String = et_loginPassword.text.toString().trim{ it <= ' '}

            loginUser(email, password)
        }
    }
}