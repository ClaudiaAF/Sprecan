package com.example.sprecan

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SettingsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//
//        // use arrayadapter and define an array
//        val arrayAdapter: ArrayAdapter<*>
//        val users = arrayOf(
//                "Virat Kohli", "Rohit Sharma", "Steve Smith",
//                "Kane Williamson", "Ross Taylor"
//        )
//
//        // access the listView from xml file
//        var mListView = findViewById<ListView>(R.id.list_view)
//        arrayAdapter = ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, users)
//        mListView.adapter = arrayAdapter
    }
}