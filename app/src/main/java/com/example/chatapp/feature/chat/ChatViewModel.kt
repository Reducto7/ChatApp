package com.example.chatapp.feature.chat

import androidx.lifecycle.ViewModel
import com.example.chatapp.feature.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel(){
    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message = _message.asStateFlow()
    private val firebaseDatabase = Firebase.database

    fun sendMessage(channelID: String, messageText: String){
        val message = Message(
            id = firebaseDatabase.reference.child("message").push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            message = messageText,
            senderName = Firebase.auth.currentUser?.displayName ?: "",

        )

        firebaseDatabase.reference.child("message").child(channelID).push().setValue(message)
    }
    fun listenForMessage(channelID: String){
        firebaseDatabase.reference.child("message").child(channelID).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach{ date ->
                        val message = date.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _message.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}