package com.example.sprecan


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.sprecan.model.User
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.google.firebase.auth.FirebaseAuth.getInstance
import kotlinx.android.synthetic.main.activity_chat_page.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class ChatPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)
        centerTitle();

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        settings_fab.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)
            startActivity(intent)
            finish()
        }


        //TODO: think about using shared preferences instead of getStringExtra
        val userId = intent.getStringExtra(Constants.LOGGED_IN_ID)

        if(userId != null){
            //get firestore data
            Firestore().getUserInfoById(this, userId)
        } else {
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

        btn_sign_out.setOnClickListener {
            getInstance()
                    .signOut()

                        startActivity(intentFor<SignInActivity>().newTask().clearTask())

        }

    }

    fun setUserInfo(user: User){
        title = user.name
    }

    private fun centerTitle() {
        val textViews: ArrayList<View> = ArrayList()
        window.decorView.findViewsWithText(textViews, title, View.FIND_VIEWS_WITH_TEXT)
        if (textViews.size > 0) {
            var appCompatTextView: AppCompatTextView? = null
            if (textViews.size === 1) {
                appCompatTextView = textViews[0] as AppCompatTextView
            } else {
                for (v in textViews) {
                    if (v.parent is Toolbar) {
                        appCompatTextView = v as AppCompatTextView
                        break
                    }
                }
            }
            if (appCompatTextView != null) {
                val params = appCompatTextView.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                appCompatTextView.layoutParams = params
                appCompatTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }
    }



    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit()
    }
}