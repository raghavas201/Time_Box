package com.example.timebox.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SessionScreen(
    email: String,
    startTime: Long,
    onLogout: () -> Unit
) {

    var elapsed by remember { mutableStateOf(0L) }

    // Format start time in readable form
    val formattedStartTime = remember(startTime) {
        SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
            .format(Date(startTime))
    }

    // Safe timer that stops automatically when screen leaves
    LaunchedEffect(startTime) {
        while (true) {
            delay(1000)
            elapsed = (System.currentTimeMillis() - startTime) / 1000
        }
    }

    val minutes = elapsed / 60
    val seconds = elapsed % 60

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Logged in as: $email")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Session started at: $formattedStartTime")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Duration: %02d:%02d".format(minutes, seconds))
            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}