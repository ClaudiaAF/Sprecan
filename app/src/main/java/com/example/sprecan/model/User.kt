package com.example.sprecan.model

import java.io.Serializable

data class User(val id: String,
                val name: String,
                val email: String,
                val bio: String) {
    constructor(): this("", "", "", "")
}