package com.cs4520.assignment5.view.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment5.service.AuthenticationService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class LoginErrorType {
    USERNAME_MISSING, PASSWORD_MISSING, INVALID_CREDENTIALS
}

class LoginViewModel : ViewModel() {
    private val authService = AuthenticationService()

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _successEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val successEvent = _successEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<LoginErrorType>(extraBufferCapacity = 1)
    val errorEvent = _errorEvent.asSharedFlow()

    fun setUsername(username: String) {
        this._username.value = username
    }

    fun setPassword(password: String) {
        this._password.value = password
    }

    fun clearCredentials() {
        this._username.value = ""
        this._password.value = ""
    }

    fun login() {
        val ok = authService.login(this.username.value ?: "", this.password.value ?: "")
        if (!ok) {
            _errorEvent.tryEmit(LoginErrorType.INVALID_CREDENTIALS)
            return
        }
        _successEvent.tryEmit(true)
    }
}