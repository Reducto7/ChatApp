package com.example.chatapp.feature.model

import androidx.compose.foundation.interaction.DragInteraction

data class Message(
    val id: String = "",
    val senderId: String = "",
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = "",
)
