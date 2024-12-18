package com.example.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.feature.auth.signin.SignInScreen
import com.example.chatapp.feature.auth.signup.SignUpScreen
import com.example.chatapp.feature.chat.ChatScreen
import com.example.chatapp.feature.home.HomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainApp(){
    Surface (modifier = Modifier.fillMaxSize()){
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "home" else "login"

        NavHost(navController = navController, startDestination = start){
            composable("login"){
                SignInScreen(navController = navController)
            }
            composable("signup"){
                SignUpScreen(navController = navController)
            }
            composable("home"){
                HomeScreen(navController = navController)
            }
            composable("chat/{channelID}&{channelName}", arguments = listOf(
                navArgument("channelID"){
                    type = NavType.StringType
                },
                navArgument("channelName"){
                    type = NavType.StringType
                }
            )){
                val channelID = it.arguments?.getString("channelID") ?: ""
                val channelName = it.arguments?.getString("channelName") ?: ""
                ChatScreen(navController = navController, channelID = channelID, channelName = channelName)
            }
        }
    }
}