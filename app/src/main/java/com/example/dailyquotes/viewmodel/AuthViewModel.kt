package com.example.dailyquotes.viewmodel



import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    fun login(username: String, password: String): Boolean {
        return username == "test" && password == "1234"
    }

    fun signup(name: String, email: String, password: String): Boolean {
        return true // fake signup
    }
}
