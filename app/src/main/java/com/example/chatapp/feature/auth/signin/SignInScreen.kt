package com.example.chatapp.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.R

@Composable
fun SignInScreen(navController: NavController){
    var  email by remember{
        mutableStateOf("")
    }
    var  password by remember{
        mutableStateOf("")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Image(
                painter = painterResource(id = R.drawable.chatimage),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier.fillMaxWidth(),
                label = {Text(text = "Email")}
            )

            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it},
                modifier = Modifier.fillMaxWidth(),
                label = {Text(text = "password")},
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.signin))
            }

            TextButton(onClick = {}) {
                Text(text = stringResource(id = R.string.signintext))
            }
        }
    }
}