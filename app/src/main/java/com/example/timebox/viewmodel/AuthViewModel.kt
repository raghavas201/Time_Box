package com.example.timebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timebox.analytics.AnalyticsLogger
import com.example.timebox.data.OtpManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val otpManager = OtpManager()

    private val _state = MutableStateFlow<AuthState>(AuthState.Login)
    val state: StateFlow<AuthState> = _state

    // Resend cooldown state
    private val _canResend = MutableStateFlow(true)
    val canResend: StateFlow<Boolean> = _canResend

    fun sendOtp(email: String) {
        val otp = otpManager.generateOtp(email)
        AnalyticsLogger.logOtpGenerated(email, otp)
        startResendCooldown()
        _state.value = AuthState.Otp(email)
    }

    fun resendOtp(email: String) {
        val otp = otpManager.generateOtp(email)
        AnalyticsLogger.logOtpGenerated(email, otp)
        startResendCooldown()
    }

    private fun startResendCooldown() {
        _canResend.value = false

        viewModelScope.launch {
            delay(30_000) // 30 seconds cooldown
            _canResend.value = true
        }
    }

    fun verifyOtp(email: String, otp: String) {
        val (isValid, reason) = otpManager.validateOtp(email, otp)

        if (isValid) {
            AnalyticsLogger.logOtpSuccess(email)
            _state.value = AuthState.Session(
                email = email,
                startTime = System.currentTimeMillis()
            )
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