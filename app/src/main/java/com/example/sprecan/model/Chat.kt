package com.example.sprecan.model

import java.io.Serializable

data class Chat(
    val chatId: String,
    val chatName: String,
    val latestMessage: String,
    val profilePicturePath: String?,
    val users: MutableList<String>
) {
    constructor() : this("", "", "", "", mutableListOf())
}