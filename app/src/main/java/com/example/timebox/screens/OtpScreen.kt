package com.example.timebox.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    email: String,
    onVerify: (String) -> Unit,
    onResend: () -> Unit,
    canResend: Boolean,
    canVerify: Boolean          // 👈 NEW
) {

    var otp by rememberSaveable { mutableStateOf("") }
    var remainingTime by remember { mutableStateOf(60) }
    var isExpired by remember { mutableStateOf(false) }
    var otpVersion by remember { mutableStateOf(0) }

    LaunchedEffect(otpVersion) {
        remainingTime = 60
        isExpired = false

        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        isExpired = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("OTP sent to: $email")

            Spacer(modifier = Modifier.height(8.dp))

            if (!isExpired) {
                Text("Expires in: ${remainingTime}s")
            } else {
                Text(
                    text = "OTP expired. Please resend.",
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Enter 6-digit OTP") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onVerify(otp) },
                enabled = !isExpired && canVerify   // 👈 DISABLE AFTER ATTEMPTS END
            ) {
                Text("Verify OTP")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    onResend()
                    otp = ""
                    otpVersion++
                },
                enabled = canResend
            ) {
                if (canResend) {
                    Text("Resend OTP")
                } else {
                    Text("Resend available in 30s")
                }
            }
        }
    }
}