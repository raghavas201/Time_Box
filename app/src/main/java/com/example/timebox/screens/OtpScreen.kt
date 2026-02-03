package com.example.timebox.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OtpScreen(email: String, onVerify: (String) -> Unit) {

    var otp by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("OTP sent to: $email")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("Enter 6-digit OTP") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { onVerify(otp) }) {
            Text("Verify OTP")
        }
    }
}