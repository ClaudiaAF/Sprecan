package com.example.sprecan.model

import java.util.*

data class Message(val chatId: String,
                   val senderId: String,
                   val message: String,
                   val sentDate: Date) {
    constructor(): this(
        "",
        "",
        "",
        Date()
    )
}