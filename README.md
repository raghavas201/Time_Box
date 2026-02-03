# 📱 Passwordless Authentication App (Email + OTP)

This project implements a **passwordless authentication flow using Email + OTP**, followed by a **session screen that tracks login duration**, built using **Kotlin + Jetpack Compose + ViewModel + Coroutines**.  
All logic is implemented **locally (no backend)** as per the assignment requirements.

---

## 🚀 Features Implemented

### ✅ 1. Email + OTP Login
- User enters an email and taps **Send OTP**
- A **6-digit OTP** is generated locally
- User enters OTP to log in

### ✅ 2. OTP Rules Implemented
- **OTP length:** 6 digits  
- **OTP expiry:** 60 seconds  
- **Maximum attempts:** 3  
- **Per-email OTP storage**
- Generating a new OTP:
  - Invalidates the old OTP
  - Resets attempt count

### ✅ 3. Resend OTP Flow
- A **Resend OTP** button is provided
- **30-second resend cooldown**
- OTP timer **restarts automatically** on resend
- If OTP expires or attempts are exhausted:
  - Old OTP is invalidated
  - User must resend to get a new OTP

### ✅ 4. Visual Countdown Timer
- 60-second visible countdown on OTP screen  
- When timer reaches 0:
  - “OTP expired” message appears
  - Verify button is disabled

### ✅ 5. Session Screen
After successful login:
- Shows **session start time (formatted)**
- Shows **live session duration (mm:ss)**
- Provides a **Logout** button
- Timer stops automatically when user logs out

---

## 🧠 OTP Logic & Expiry Handling

### How OTP is handled internally (`OtpManager.kt`):
- OTPs are stored in a `MutableMap<String, OtpData>` where:
  - Key = user email
  - Value = `OtpData(otp, expiryTime, attemptsLeft)`
- When OTP is generated:
  - Expiry is set to `currentTimeMillis + 60_000`
  - Attempts are set to 3
- During validation:
  - If expired → OTP is deleted and user must resend
  - If attempts = 0 → OTP is deleted and user must resend
  - If correct → login succeeds
  - If incorrect → attempts are decremented

---

## 📦 Data Structures Used & Why

### `MutableMap<String, OtpData>`
Used because:
- Fast lookup by email (`O(1)`)
- Easy to update per-user OTP
- Suitable for local in-memory storage

### `OtpData`
Stores:
- `otp: String` → actual OTP
- `expiryTime: Long` → when OTP expires
- `attemptsLeft: Int` → remaining tries

This cleanly separates OTP data from business logic.

---

## 📊 External SDK Used: **Timber (Logging Library)**

### Why Timber?
- Lightweight and industry-standard
- Easy to integrate
- Provides clean, structured logs
- No heavy setup like Firebase

### Logged Events:
- OTP generated (including actual OTP in Logcat for testing)
- OTP validation success
- OTP validation failure (with reason)
- User logout

---

## 🏗️ Architecture Used

### ✅ MVVM Pattern
- **UI Layer:** Jetpack Compose screens (`LoginScreen`, `OtpScreen`, `SessionScreen`)
- **ViewModel:** `AuthViewModel` handles all business logic
- **State Management:** One-way data flow using `StateFlow<AuthState>`
- **Sealed Class UI States (`AuthState`)**
  - `Login`
  - `Otp(email)`
  - `Session(email, startTime)`

---

## 🎨 UI Implementation (Jetpack Compose)
Key concepts used:
- `@Composable`
- `remember` and `rememberSaveable`
- `LaunchedEffect` for timers
- State hoisting
- Proper recomposition handling
- Centered UI layout using `Box + Column`

---

## 🤖 What I Used GPT For vs What I Implemented

### Used GPT for:
- Initial project structure suggestions
- Boilerplate Compose setup
- README formatting ideas

### Implemented Myself:
- OTP generation and validation logic
- Resend cooldown mechanism
- Countdown timer behavior
- ViewModel state management
- Timber analytics integration
- UI centering and layout
- Session timer logic

---

## ▶️ How to Run the App

1. Open project in **Android Studio**
2. Sync Gradle
3. Run on emulator or physical device
4. Enter email → Click **Send OTP**
5. Check **Logcat (filter: Analytics)** to see generated OTP
6. Enter OTP → Login
7. Observe session timer
8. Click **Logout** to return to login screen
---

## ✅ What I Used GPT For vs What I Implemented Myself

### 🧠 Work Assisted by GPT

I used GPT for guidance in the following areas:
- Initial project structure suggestions  
- Best practices for Jetpack Compose UI layout  
- Ideas for countdown timer implementation  
- Formatting and structuring of this README file  
- Suggestions for improving code clarity and readability  

### 💻 Work Implemented by Me

I personally designed and implemented:
- The core OTP generation and validation logic  
- The attempt tracking and expiry mechanism  
- The resend OTP flow with cooldown  
- The ViewModel state management using StateFlow  
- The MVVM architecture and separation of concerns  
- The UI behavior (disabling buttons on expiry/attempt exhaustion)  
- The session timer logic that survives recompositions  
- Integration of Timber logging into the authentication flow  

GPT was used as a learning and debugging aid, but the core logic, design decisions, and final implementation were done by me.
