package com.example.timebox.data

import kotlin.random.Random

data class OtpData(
    val otp: String,
    val expiryTime: Long,
    var attemptsLeft: Int
)

class OtpManager {

    private val otpStore = mutableMapOf<String, OtpData>()

    fun generateOtp(email: String): String {

        val otp = Random.nextInt(100000, 999999).toString()
        val expiry = System.currentTimeMillis() + 60_000 // 60 seconds

        otpStore[email] = OtpData(
            otp = otp,
            expiryTime = expiry,
            attemptsLeft = 3
        )

        return otp
    }

    fun validateOtp(email: String, enteredOtp: String): Pair<Boolean, String> {

        val data = otpStore[email] ?: return false to "No OTP generated"

        // 1) Check expiry first
        if (System.currentTimeMillis() > data.expiryTime) {
            return false to "OTP expired"
        }

        // 2) Check attempts
        if (data.attemptsLeft <= 0) {
            return false to "Max attempts exceeded"
        }

        // 3) Validate OTP
        return if (enteredOtp == data.otp) {
            true to "Success"
        } else {
            data.attemptsLeft--
            false to "Incorrect OTP (${data.attemptsLeft} left)"
        }
    }

    fun clearOtp(email: String) {
        otpStore.remove(email)
    }
}