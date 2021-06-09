package com.example.sprecan

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sprecan.glide.GlideApp
import com.example.sprecan.utils.Constants
import com.example.sprecan.utils.Firestore
import com.example.sprecan.utils.Storage
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.profile_layout.*
import org.jetbrains.anko.*
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class UserAccount : AppCompatActivity() {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"

    //notification id
    var NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        createNotificationChannel()

        //create intent pending
        val intent = Intent(this, UserAccount::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        editProfileImg.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                }
                startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
            }

        NOTIFICATION_ID = Random.nextInt()

        //create notification Say Hello
        val notificationH = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Profile Particulars Amended")
            .setContentText("Amendments were successfully made to thou profile.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) //closes notification onclick
            .setContentIntent(pendingIntent) //navigates to app
            .build()

        val notificationHAY = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Account Banishment Successful")
            .setContentText("Gain acceptance to return to thou account.")
            .setSmallIcon(R.drawable.square_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) //closes notification onclick
            .setContentIntent(pendingIntent) //navigates to app
            .build()


        val notificationManager = NotificationManagerCompat.from(this)
        btn_save.setOnClickListener {
            if (::selectedImageBytes.isInitialized)
                Storage.uploadProfilePhoto(selectedImageBytes) { imagePath ->
                    Firestore().updateCurrentUser(editText_name.text.toString(),imagePath)
                }
            else
                Firestore().updateCurrentUser(editText_name.text.toString())
            notificationManager.notify(++NOTIFICATION_ID, notificationH)
        }

        btn_sign_out.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this@UserAccount)
                .addOnCompleteListener {
                    startActivity(intentFor<SignInActivity>().newTask().clearTask())
                    notificationManager.notify(++NOTIFICATION_ID, notificationHAY)
                }
        }

    }

    fun createNotificationChannel(){
        //check the SDK VERSION greater than oreo
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create a notification channel and send the id, name and importance
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                //set the notification LED color
                enableLights(true)
                lightColor = Color.YELLOW
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                    .getBitmap(contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectedImageBytes)
                    .into(user_profile_img)

            pictureJustChanged = true
        }
    }

    override fun onStart() {
        super.onStart()
        Firestore().getCurrentUser { user ->
                editText_name.setText(user.name)
                if (!pictureJustChanged && user.profilePicturePath != null)
                    GlideApp.with(this)
                        .load(Storage.pathToReference(user.profilePicturePath))
                        .placeholder(R.drawable.ic_baseline_person_24)
                        .into(user_profile_img)
        }
    }
}