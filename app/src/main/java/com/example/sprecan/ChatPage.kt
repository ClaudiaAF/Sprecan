package com.example.sprecan


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.example.sprecan.model.User
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore


class ChatPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //TODO: think about using shared preferences instead of getStringExtra
        val userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if(userId != null){
            //get firestore data
            Firestore().getUserInfoById(this, userId)
        } else {
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

    }

    fun setUserInfo(user: User){
        title = user.name
    }


}