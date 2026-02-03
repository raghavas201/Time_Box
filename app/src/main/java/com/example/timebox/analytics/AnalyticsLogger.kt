package com.example.timebox.analytics

import timber.log.Timber

object AnalyticsLogger {

    fun logOtpGenerated(email: String, otp: String) {
        Timber.tag("Analytics")
            .d("OTP for $email = $otp")   // 👈 THIS IS WHAT YOU WILL SEE IN LOGCAT
    }

    fun logOtpSuccess(email: String) {
        Timber.tag("Analytics")
            .d("OTP validated successfully for: $email")
    }

    fun logOtpFailure(email: String, reason: String) {
        Timber.tag("Analytics")
            .d("OTP failed for: $email, Reason: $reason")
    }

    fun logLogout(email: String) {
        Timber.tag("Analytics")
            .d("User logged out: $email")
    }
}