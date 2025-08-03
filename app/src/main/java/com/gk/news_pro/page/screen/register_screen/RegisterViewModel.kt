package com.gk.news_pro.page.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.news_pro.data.model.User
import com.gk.news_pro.data.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun register() {
        when {
            username.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() -> {
                _uiState.value = RegisterUiState.Error("Vui lòng điền đầy đủ thông tin")
            }
            !isValidEmail(email.value) -> {
                _uiState.value = RegisterUiState.Error("Email không hợp lệ")
            }
            password.value != confirmPassword.value -> {
                _uiState.value = RegisterUiState.Error("Mật khẩu không khớp")
            }
            password.value.length < 6 -> {
                _uiState.value = RegisterUiState.Error("Mật khẩu phải có ít nhất 6 ký tự")
            }
            else -> {
                _uiState.value = RegisterUiState.Loading
                viewModelScope.launch {
                    try {
                        authService.register(
                            User(
                                id = "", // Backend sẽ sinh ID
                                email = email.value,
                                password = password.value,
                                username = username.value
                            )
                        )
                        _uiState.value = RegisterUiState.Success
                    } catch (e: Exception) {
                        _uiState.value = RegisterUiState.Error(e.message ?: "Đăng ký thất bại")
                    }
                }
            }
        }
    }

    fun resetUiState() {
        _uiState.value = RegisterUiState.Idle
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )
        return emailPattern.matcher(email).matches()
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}