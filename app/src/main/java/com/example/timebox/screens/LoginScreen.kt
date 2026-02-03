package com.example.timebox.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onSendOtp: (String) -> Unit) {

    var email by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter Email") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { onSendOtp(email) }) {
                Text("Send OTP")
            }
        }
    }
}