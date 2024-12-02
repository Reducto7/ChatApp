package com.example.chatapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.ui.theme.Purple
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.chatapp.ui.theme.DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.state.collectAsState()

    val channels = viewModel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    LaunchedEffect (key1 = uiState.value){
        if(uiState.value == SignOutState.LoggedOut)
            navController.navigate("login"){
                popUpTo("home") { inclusive = true }
            }
    }
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Purple)
                    .clickable {
                        addChannel.value = true
                    }
            ) {
                Text(
                    text = "Add Channel",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        },
        containerColor = Color.Black
    ) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ){
            LazyColumn {
                item {
                    Row (
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Messages",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 20.sp,

                            ),
                            modifier = Modifier.weight(1f )
                        )
                        IconButton(onClick = { viewModel.signOut() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }
                items(channels.value){ channel ->
                    ChannelItem(
                        channel.name,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)){
                        navController.navigate("chat/${channel.id}&${channel.name}")
                    }
                }
            }

        }
        if (addChannel.value){
            ModalBottomSheet (
                onDismissRequest = {},
                sheetState = sheetState
                ){
                AddChannelDialog{
                    viewModel.addChannel(it )
                    addChannel.value = false
                }
            }
        }
    }
}

@Composable
fun ChannelItem(channelName: String, modifier: Modifier, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGrey)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(alpha = 0.3f))
        ){
            Text(
                text = channelName[0].uppercase(),
                color = Color.White,
                style = TextStyle(
                    fontSize = 35.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = channelName,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp
            )
        )

    }
}

@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit){
    var channelName by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = channelName,
            onValueChange = {channelName = it},
            label = { Text(text = "Channel Name") },
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { onAddChannel(channelName) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }
}