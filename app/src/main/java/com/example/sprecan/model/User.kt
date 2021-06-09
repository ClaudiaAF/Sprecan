package com.example.sprecan.model

import com.google.firebase.firestore.model.DatabaseId
import java.io.Serializable

data class User(val id: String,
                val name: String,
                val profilePicturePath: String?) {
    constructor(): this("", "", null)
}