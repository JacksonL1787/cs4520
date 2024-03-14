package com.cs4520.assignment4.authentication.login

import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.authentication.AuthenticationService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class LoginErrorType {
    USERNAME_MISSING, PASSWORD_MISSING, INVALID_CREDENTIALS
}

class LoginViewModel: ViewModel() {
    private val authService = AuthenticationService()

    private var username: String = "";
    private var password: String = "";

    private val _successEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val successEvent = _successEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<LoginErrorType>(extraBufferCapacity = 1)
    val errorEvent = _errorEvent.asSharedFlow()

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun login() {
        // Check if username or password are empty
        val ok = authService.login(username, password)
        println(ok)
        if (!ok) {
            _errorEvent.tryEmit(LoginErrorType.INVALID_CREDENTIALS)
            return
        }
        _successEvent.tryEmit(true)
    }
}