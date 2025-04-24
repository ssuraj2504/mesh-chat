package com.example.meshchat.model

data class Message(
    val sender: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
