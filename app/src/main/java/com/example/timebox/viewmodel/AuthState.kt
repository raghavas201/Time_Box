package com.example.timebox.viewmodel
sealed class AuthState {
    object Login : AuthState()
    data class Otp(val email: String) : AuthState()
    data class Session(val email: String, val startTime: Long) : AuthState()
}