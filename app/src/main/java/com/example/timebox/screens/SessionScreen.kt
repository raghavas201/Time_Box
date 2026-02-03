package com.example.timebox.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(email: String, startTime: Long, onLogout: () -> Unit) {

    var elapsed by remember { mutableStateOf(0L) }

    LaunchedEffect(startTime) {
        while (true) {
            delay(1000)
            elapsed = (System.currentTimeMillis() - startTime) / 1000
        }
    }

    val minutes = elapsed / 60
    val seconds = elapsed % 60

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Logged in as: $email")
        Text("Session started at: $startTime")
        Text("Duration: %02d:%02d".format(minutes, seconds))

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}