package com.example.nimma_guru.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimma_guru.model.User
import com.example.nimma_guru.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(email: String, pass: String, role: String) {
        viewModelScope.launch {
            if (email.isBlank() || pass.isBlank()) {
                _authState.value = AuthState.Error("Please enter email and password")
                return@launch
            }
            _authState.value = AuthState.Loading
            val user = repository.login(email, pass, role)
            if (user != null) {
                _currentUser.value = user
                _authState.value = AuthState.Success(user.role, user.name)
            } else {
                _authState.value = AuthState.Error("Invalid credentials for this role")
            }
        }
    }

    fun register(name: String, email: String, pass: String, role: String) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank() || pass.length < 4) {
                _authState.value = AuthState.Error("Please fill all fields (password min 4 chars)")
                return@launch
            }
            _authState.value = AuthState.Loading
            
            // Unique credential check
            val existingUser = repository.getUserByCredentials(email, pass)
            if (existingUser != null) {
                _authState.value = AuthState.Error("This account already exists for another role. Please use different credentials.")
                return@launch
            }

            val newUser = User(
                uid = UUID.randomUUID().toString(),
                name = name,
                email = email,
                password = pass,
                role = role
            )
            repository.register(newUser)
            _currentUser.value = newUser
            _authState.value = AuthState.Success(role, name)
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val role: String, val name: String) : AuthState()
    data class Error(val message: String) : AuthState()
}