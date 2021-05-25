package com.example.sprecan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sprecan.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            startActivity<SignInActivity>()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.LOGGED_IN_ID, user.uid)
            startActivity(intent)
        }

        finish()
    }
}