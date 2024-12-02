package com.example.chatapp.feature.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.feature.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){
    private val firebaseDatabase = Firebase.database
    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    private val _state = MutableStateFlow<SignOutState>(SignOutState.Nothing)
    val state = _state.asStateFlow()

    init {
        getChannel()
    }

    private  fun  getChannel(){
        firebaseDatabase.getReference("channel").get()
            .addOnSuccessListener{
                val list = mutableListOf<Channel>()
                it.children.forEach{ date ->
                    val channel = Channel(date.key!!, date.value.toString())
                    list.add(channel)
                }
                _channels.value = list
            }
    }

    fun addChannel(name: String){
        val key = firebaseDatabase.getReference("chennel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name)
            .addOnSuccessListener {
                getChannel()
            }

    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
        _state.value = SignOutState.LoggedOut
    }
}

sealed class SignOutState{
    object Nothing: SignOutState()
    object LoggedOut: SignOutState()
}