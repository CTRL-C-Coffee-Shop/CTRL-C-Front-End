package com.example.ctrl_c.viewmodel.authetntication.login

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}