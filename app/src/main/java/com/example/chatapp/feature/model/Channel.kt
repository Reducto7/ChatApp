package com.example.chatapp.feature.model

data class Channel(
    val id: String = "",
    val name: String,
    val createAt: Long = System.currentTimeMillis()
)
