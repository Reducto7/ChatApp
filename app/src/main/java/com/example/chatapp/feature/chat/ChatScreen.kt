package com.example.chatapp.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.contentValuesOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.feature.home.ChannelItem
import com.example.chatapp.feature.model.Message
import com.example.chatapp.ui.theme.DarkGrey
import com.example.chatapp.ui.theme.Purple
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChatScreen(navController: NavController, channelID: String, channelName: String){
    Scaffold (
        containerColor = Color.Black
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ){
            val viewModel: ChatViewModel = hiltViewModel()
            LaunchedEffect (key1 = true){
                viewModel.listenForMessage(channelID)
            }
            val messages = viewModel.message.collectAsState()
            ChatMessage(
                channelName = channelName,
                messages = messages.value,
                onSendMessage = {message ->
                    viewModel.sendMessage(channelID, message)
                },
                onClickBackArrow = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ChatMessage(
    channelName: String,
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onClickBackArrow: () -> Unit,
){
    var sentence by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn (modifier = Modifier.weight(1f)){
            item{
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(onClick = onClickBackArrow) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    ChannelItem(channelName = channelName, modifier = Modifier) {

                    }
                }
            }
            items(messages){ msg ->
                ChatBubble(message = msg)
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = sentence,
                onValueChange = { sentence = it},
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                onSendMessage(sentence)
                sentence = ""
            }) {
                Image(painter = painterResource(id = R.drawable.send) , contentDescription = null )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message){
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val  bubbleColor = if (isCurrentUser){
        Purple
    } else {
        DarkGrey
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        val  alignment = if (!isCurrentUser)Alignment.CenterStart else Alignment.CenterEnd
        Row (
            modifier = Modifier
                .padding(8.dp)
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (!isCurrentUser){
                Image(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp))
            }
            Text(
                text = message.message.trim(),
                color = Color.White,
                modifier = Modifier.background(color = bubbleColor, shape = RoundedCornerShape(8.dp)).padding(16.dp)
                )
        }
    }
}