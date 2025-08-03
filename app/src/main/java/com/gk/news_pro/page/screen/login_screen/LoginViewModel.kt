package com.gk.news_pro.page.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.news_pro.data.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        if (email.value.isBlank() || password.value.isBlank()) {
            _uiState.value = LoginUiState.Error("Email và mật khẩu không được để trống")
            return
        }

        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            try {
                val token = authService.login(email.value, password.value)
                if (token != null) {
                    _uiState.value = LoginUiState.Success
                    Log.d("LoginViewModel", "Login successful, token: $token")
                } else {
                    _uiState.value = LoginUiState.Error("Đăng nhập thất bại: Sai email hoặc mật khẩu")
                    Log.e("LoginViewModel", "Login failed: No token returned")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Đăng nhập thất bại")
                Log.e("LoginViewModel", "Login error: ${e.message}", e)
            }
        }
    }

    fun resetUiState() {
        _uiState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}