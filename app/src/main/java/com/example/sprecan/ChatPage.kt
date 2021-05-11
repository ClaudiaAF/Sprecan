package com.example.sprecan


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sprecan.utils.Constants
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily


class ChatPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //TODO: think about using shared preferences instead of getStringExtra
        val userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if(userId != null){
            //get firestore data
        } else {
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

    }
}