package com.example.timebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otpapp.analytics.AnalyticsLogger
import com.example.otpapp.data.OtpManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val otpManager = OtpManager()

    private val _state = MutableStateFlow<AuthState>(AuthState.Login)
    val state: StateFlow<AuthState> = _state

    fun sendOtp(email: String) {
        otpManager.generateOtp(email)
        AnalyticsLogger.logOtpGenerated(email)
        _state.value = AuthState.Otp(email)
    }

    fun verifyOtp(email: String, otp: String) {
        val (isValid, reason) = otpManager.validateOtp(email, otp)

        if (isValid) {
            AnalyticsLogger.logOtpSuccess(email)
            _state.value = AuthState.Session(email, System.currentTimeMillis())
        } else {
            AnalyticsLogger.logOtpFailure(email, reason)
        }
    }

    fun logout(email: String) {
        otpManager.clearOtp(email)
        AnalyticsLogger.logLogout(email)
        _state.value = AuthState.Login
    }
}