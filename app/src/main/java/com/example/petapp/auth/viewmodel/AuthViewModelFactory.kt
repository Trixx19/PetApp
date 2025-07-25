package com.example.petapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petapp.auth.data.AuthRepository

class AuthViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}