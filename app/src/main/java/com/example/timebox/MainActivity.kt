package com.example.timebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.timebox.screens.LoginScreen
import com.example.timebox.screens.OtpScreen
import com.example.timebox.screens.SessionScreen
import com.example.timebox.ui.theme.TimeBoxTheme
import com.example.timebox.viewmodel.AuthState
import com.example.timebox.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TimeBoxTheme {

                val viewModel: AuthViewModel = viewModel()
                val state = viewModel.state.collectAsState().value

                when (state) {

                    is AuthState.Login -> {
                        LoginScreen { email ->
                            viewModel.sendOtp(email)
                        }
                    }

                    is AuthState.Otp -> {
                        val canResend = viewModel.canResend.collectAsState().value

                        OtpScreen(
                            email = state.email,
                            onVerify = { otp ->
                                viewModel.verifyOtp(state.email, otp)
                            },
                            onResend = {
                                viewModel.resendOtp(state.email)
                            },
                            canResend = canResend
                        )
                    }

                    is AuthState.Session -> {
                        SessionScreen(
                            email = state.email,
                            startTime = state.startTime,
                            onLogout = {
                                viewModel.logout(state.email)
                            }
                        )
                    }
                }
            }
        }
    }
}