package com.example.timebox.data
import kotlin.random.Random
import System.currentTimeMillis

data class OtpData(
    val otp: String,
    val expiryTime: Long,
    var attemptsLeft: Int
)

class OtpManager {

    private val otpStore = mutableMapOf<String, OtpData>()

    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()
        val expiry = currentTimeMillis() + 60_000 // 60 seconds

        otpStore[email] = OtpData(
            otp = otp,
            expiryTime = expiry,
            attemptsLeft = 3
        )

        return otp
    }

    fun validateOtp(email: String, enteredOtp: String): Pair<Boolean, String> {
        val data = otpStore[email] ?: return false to "No OTP generated"

        if (currentTimeMillis() > data.expiryTime) {
            return false to "OTP expired"
        }

        if (data.attemptsLeft <= 0) {
            return false to "Max attempts exceeded"
        }

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